package finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import finance.boundaries.FundBoundary;
import finance.boundaries.NewFundDetails;
import finance.logic.FundService;

@RestController
public class FundController {

	private FundService fundService;
	private final String pageSize = "10";

	@Autowired
	public void setFundService(FundService fundService) {
		this.fundService = fundService;
	}

	@RequestMapping(path = "/fund", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public FundBoundary createFund(@RequestBody NewFundDetails details) {
		FundBoundary fund = new FundBoundary();
		fund.setFundId(details.getFundId());
		fund.setFundName(details.getFundName());
		fund.setFundIcon(details.getFundIcon());
		return fundService.createFund(fund);
	}

	@RequestMapping(path = "/fund/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FundBoundary[] getAllFunds(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = pageSize) int size) {
		return fundService.getAllFunds(page, size).toArray(new FundBoundary[0]);
	}
}
