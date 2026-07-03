package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class CompanyController {
private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @PostMapping("/companies")
    public ResponseEntity<Company> CreateCompanies(@Valid @RequestBody Company com) throws Exception {
        //TODO: process POST request
        Company newCompany = this.companyService.createCompany(com);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }
    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> getAllCompany(
        @Filter Specification<Company> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.companyService.getAllCompanies(spec, pageable));
    }

    @PutMapping("companies/{id}")
    public ResponseEntity<Company> updateCompanies(@PathVariable("id") long id, @RequestBody Company company) {
        company.setId(id);
        Company checkCompany = this.companyService.updateCompany(company);
        if (checkCompany == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(checkCompany);
        }
    }
    @DeleteMapping("companies/{id}")
    public ResponseEntity<String> deleteCompanies(@PathVariable("id") long id) {
        Company checkCompany = this.companyService.isExisCompany(id);
        if (checkCompany == null) {
            return ResponseEntity.notFound().build();
        } else {
            this.companyService.deleteCompany(id);
            return ResponseEntity.ok("Xóa thành công");
        }
    }
    
}
