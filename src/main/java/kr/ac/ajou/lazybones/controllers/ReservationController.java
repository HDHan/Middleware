package kr.ac.ajou.lazybones.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import kr.ac.ajou.lazybones.components.WasherManager;
import kr.ac.ajou.lazybones.repos.entities.RealReservation;
import kr.ac.ajou.lazybones.washerapp.Washer.Reservation;
import kr.ac.ajou.lazybones.washerapp.Washer.ReservationQueue;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReservationController {

	@Autowired
	WasherManager washerManager;

	@RequestMapping(value = "/Reservation", method = RequestMethod.GET)
	public String showReservation(HttpServletRequest request, Model model) {
		
		// User ID (session)
		String uid = (String) request.getSession().getAttribute("userid");

		//Reservations (Expected 'real' time rather than duration)
		Map<String, RealReservation[]> map = washerManager.getRealReservationsBy(uid);

		model.addAttribute("reservations", map);

		return "reservationList";
	}
}
