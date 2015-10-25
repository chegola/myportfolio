package com.nadee.myportfolio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nadee.myportfolio.core.TradeManagerImpl;
import com.nadee.myportfolio.core.UserParameter;
import com.nadee.myportfolio.factory.UserParameterFactory;
import com.nadee.myportfolio.model.Portfolio;
import com.nadee.myportfolio.repositories.CashBalanceRepository;
import com.nadee.myportfolio.repositories.PortfolioRepository;
import com.nadee.myportfolio.repositories.SecurityRepository;
import com.nadee.myportfolio.repositories.TxnRepository;

@Controller
public class MyPortfolioController {

	@Autowired
	PortfolioRepository portfolioRepo;
	@Autowired
	TradeManagerImpl tradeMgr;
	@Autowired
	CashBalanceRepository cashBalRepo;
	@Autowired
	TxnRepository txnRepo;
	@Autowired
	SecurityRepository secRepo;
	@Autowired
	UserParameterFactory userParamFactory;

	@RequestMapping(value = "/myPortfolio", method = RequestMethod.GET)
	public String hellox(ModelMap model) {
		Portfolio portfolio = portfolioRepo.findByName("Che");
		// model.addAttribute("name", portfolio.getName());
		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("name", portfolio.getName());
		myModel.put("holdings", tradeMgr.getHolding(portfolio));
		myModel.put("dividends", tradeMgr.getDividend(portfolio));
		myModel.put("cash", cashBalRepo.findByPortfolioId(portfolio.getPortfolioId()));
		myModel.put("deposits", txnRepo.findDepositByPortfolio(portfolio));
		myModel.put("symbols", secRepo.findAll(new Sort(Sort.Direction.ASC, "symbol")));
		myModel.put("userParam", userParamFactory.createUserParameter());

		model.addAllAttributes(myModel);
		return "helloWorld";
	}

	@RequestMapping(value = "/myPortfolio/addTxn", method = RequestMethod.POST)
	public String addTxn(@ModelAttribute("userParam") UserParameter userParam, ModelMap model) throws Exception{
		userParam.setPortfolioId(1);
		tradeMgr.doTrade(userParam);
		return "redirect:/myPortfolio";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(true);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	/*
	 * @RequestMapping(value = "/myPortfolio/addTxn", method =
	 * RequestMethod.POST) public String
	 * addTxn(@ModelAttribute("transactionModel")UserParameter userParam,
	 * BindingResult result, ModelMap model) throws Exception {
	 * tradeMgr.doTrade(userParam); return "helloWorld"; }
	 */
}