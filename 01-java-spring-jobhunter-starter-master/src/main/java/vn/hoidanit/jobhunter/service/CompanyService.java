package vn.hoidanit.jobhunter.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.util.SecurityUtil;
@Service
public class CompanyService {
private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
    public Company createCompany(Company company) {
        return this.companyRepository.save(company);
    }
    public ResultPaginationDTO getAllCompanies(Pageable pageable) {
        Page<Company> page = this.companyRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        Meta meta = new Meta();
        
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        
        result.setMeta(meta);
        result.setResult(page.getContent());
        return result;
    }
    public Company isExisCompany(long id) {
        return this.companyRepository.findById(id).orElse(null);
    }
    public Company updateCompany(Company company) {
        Company check = this.isExisCompany(company.getId());
        if(check == null)
        {
            return null;
        }
        check.setName(company.getName());
        check.setAddress(company.getAddress());
        check.setLogo(company.getLogo());
        check.setDescription(company.getDescription());
        check.setUpdatedBy(SecurityUtil.getCurrentUserLogin().isPresent() == true
            ? SecurityUtil.getCurrentUserLogin().get() : "");
        return this.companyRepository.save(check);
    }
    public void deleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
