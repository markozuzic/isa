package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Demand;
import com.example.model.Manager;
import com.example.model.Offer;
import com.example.model.Supplier;
import com.example.repository.DemandRepository;
import com.example.repository.OfferRepository;
import com.example.repository.SupplierRepository;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private DemandRepository demandRepository;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public String createOffer(Offer newOffer) {
		if (offerRepository.findOne(newOffer.getId()) == null) {
			offerRepository.save(newOffer);
			return "OK";
		}
		return "Id error";
	}

	@Override
	public List<Offer> getOfferForDemands() {
		Manager m = (Manager) httpSession.getAttribute("manager");
		List<Demand> d = demandRepository.findByRestaurantId(m.getRestaurantId());
		ArrayList<Offer> offers = new ArrayList<Offer>();
		for (Demand demand : d) {
			offers.addAll(offerRepository.findByDemandId(demand.getId()));
		}
		return offers;
	}

	@Override
	public String approveOffer(String id) {
		Long offerId = Long.parseLong(id);
		Offer offer = offerRepository.findOne(offerId);
		Supplier s = supplierRepository.findOne(offer.getSupplierId());
		Demand d = demandRepository.findOne(offer.getDemandId());
		d.setActive(false);
		demandRepository.save(d);
		
		send(s.getEmail(), true, offerId);
		
		List<Offer> offers = offerRepository.findByDemandId(offer.getDemandId());
		for (Offer of : offers) {
			if(of.getId() != offerId) {
				Supplier supplier = supplierRepository.findOne(of.getSupplierId());
				send(supplier.getEmail(), false, of.getId());
			}
			of.setResponded(true);
			offerRepository.save(of);
		}
		return "OK";
	}
	
	 private void send(String emailAdress, boolean accepted, Long offerId) {
		
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(emailAdress);
            helper.setReplyTo("someone@localhost");
            helper.setFrom("someone@localhost");
            helper.setSubject("Ponuda");
	    if(accepted) {
		     helper.setText("Vaša ponuda sa rednim brojem " + offerId + " je prihvaćena!");
		} else {
		    helper.setText("Vaša ponuda sa rednim brojem " + offerId + " nije prihvaćena.");
		}	
            
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}
        javaMailSender.send(mail);
    }

}
