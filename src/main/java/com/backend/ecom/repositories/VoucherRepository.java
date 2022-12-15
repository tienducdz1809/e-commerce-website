package com.backend.ecom.repositories;

import com.backend.ecom.entities.Voucher;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Boolean existsByName(String name);
}
