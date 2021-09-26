package com.gianvittorio.insurancewallet.common.service;

import com.gianvittorio.insurancewallet.common.web.model.b2b.Company;

public interface CompanyService {

    Company getCompanyById(final Long id);

    Company getCompanyByName(final String name);

    Company getCompanyByDocument(final String document);

    Company addCompany(final Company company);

    Company updateCompany(final Long id, final Company company);
}
