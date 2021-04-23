package com.iiht.estock.company.service;

import com.iiht.estock.company.exception.CompanyNotCreatedException;
import com.iiht.estock.company.exception.CompanyNotFoundException;
import com.iiht.estock.company.model.Company;
import com.iiht.estock.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public Company addCompany(Company company) throws CompanyNotCreatedException {
        try {
            return companyRepository.save(company);
        } catch (Exception e) {
            log.error(e.getClass() + " -- " + e.getMessage());
            throw new CompanyNotCreatedException(e.getMessage());
        }
    }

    @Override
    public Company getCompany(Long companyCode) throws CompanyNotFoundException {
        try {
            Optional<Company> optionalCompany = companyRepository.findById(companyCode);
            Company company = optionalCompany.orElseThrow(() -> new CompanyNotFoundException("Company Code " + companyCode + " not found."));
            company.setLatestStockPrice(restTemplate.getForEntity("http://stock-price-service:8090/api/v1.0/market/stock/get/" + companyCode, Long.class).getBody());
            return company;
        } catch (Exception e) {
            log.error(e.getClass() + " -- " + e.getMessage());
            throw new CompanyNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Company> getAllCompanies() throws CompanyNotFoundException {
        List<Long> companyCodes = companyRepository.findAllId();
        List<Company> companies = new ArrayList<>();
        for (long companyCode :
                companyCodes) {
            companies.add(getCompany(companyCode));
        }
        return companies;
    }

    @Override
    public void deleteCompany(Long companyCode) throws CompanyNotFoundException {
        Optional<Company> optionalCompany = companyRepository.findById(companyCode);
        Company company = optionalCompany.orElseThrow(() -> new CompanyNotFoundException("Company Code " + companyCode + " not found."));
        restTemplate.delete("http://stock-price-service:8090/api/v1.0/market/stock/delete/" + companyCode);
        companyRepository.delete(company);
    }
}
