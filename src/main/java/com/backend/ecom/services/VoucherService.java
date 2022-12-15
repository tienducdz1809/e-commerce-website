package com.backend.ecom.services;

import com.backend.ecom.dto.voucher.VoucherDTO;
import com.backend.ecom.dto.voucher.VoucherRequestDTO;
import com.backend.ecom.entities.Voucher;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }

    public ResponseEntity<ResponseObject> getVoucherDetail(Long id) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found voucher with id:" + id));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Voucher details found!", new VoucherDTO(voucher)));
    }

    public ResponseEntity<ResponseObject> createVoucher(VoucherRequestDTO voucherRequest) {
        boolean exist = voucherRepository.existsByName(voucherRequest.getName());
        if(exist){
            throw new ResourceNotFoundException("Voucher is already existed");
        }
        if (voucherRequest.getEndDate().isBefore(LocalDate.now())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", "Voucher is outdated", voucherRequest.getEndDate()));
        }
        Voucher voucher = new Voucher(voucherRequest);
        voucherRepository.save(voucher);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "New voucher created",new VoucherDTO(voucher)));

    }

    public ResponseEntity<ResponseObject> updateVoucher(Long id, VoucherRequestDTO voucherRequest) {
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found voucher with id:" + id));

        if (voucherRequest.getEndDate().isBefore(LocalDate.now())){
            if (voucherRequest.getEndDate().isBefore(LocalDate.now())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("error", "Voucher is outdated", voucherRequest.getEndDate()));
            }
        }
        voucher.setName(voucherRequest.getName());
        voucher.setDescription(voucherRequest.getDescription());
        voucher.setStartDate(voucherRequest.getStartDate());
        voucher.setEndDate(voucherRequest.getEndDate());
        voucher.setReductionPercentage(voucherRequest.getReductionPercentage());
        voucher.setMaxReduction(voucherRequest.getMaxReduction());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Voucher updated!",new VoucherDTO(voucherRepository.save(voucher))));
    }

    public ResponseEntity<ResponseObject> deleteVoucher(Long id) {
        if (!voucherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found voucher with id:" + id);
        }
        voucherRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete voucher successfully", ""));

    }

}
