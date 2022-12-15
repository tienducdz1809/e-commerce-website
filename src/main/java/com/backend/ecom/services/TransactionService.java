package com.backend.ecom.services;

import com.backend.ecom.dto.transaction.TransactionDTO;
import com.backend.ecom.dto.transaction.TransactionRequestDTO;
import com.backend.ecom.entities.Cart;
import com.backend.ecom.entities.Transaction;
import com.backend.ecom.entities.User;
import com.backend.ecom.entities.Voucher;
import com.backend.ecom.exception.ResourceNotFoundException;
import com.backend.ecom.payload.response.ResponseObject;
import com.backend.ecom.repositories.TransactionRepository;
import com.backend.ecom.repositories.UserRepository;
import com.backend.ecom.repositories.VoucherRepository;
import com.backend.ecom.supporters.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public ResponseEntity<ResponseObject> getTransactionDetail(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found transaction with id:" + id));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete transaction successfully", new TransactionDTO(transaction)));
    }

    @Transactional
    public ResponseEntity<ResponseObject> createTransaction(TransactionRequestDTO transactionRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsernameAndDeleted(username, false)
                .orElseThrow(() -> new ResourceNotFoundException("Not found username: " + username));

        Cart cart = user.getCart();
        double price = cart.getTotalItemPrice();

        Voucher voucher;
        if (transactionRequest.getVoucherID()!=0){
             voucher = voucherRepository.findById(transactionRequest.getVoucherID())
                    .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: "+transactionRequest.getVoucherID()));
            price = Voucher.applyVoucher(price, voucher);

        } else voucher=null;

        Transaction transaction = new Transaction(user, transactionRequest.getPaymentType(), transactionRequest.getStatus(), transactionRequest.getMessage(), voucher, price, cart, null);
        transactionRepository.save(transaction);
        user.getCart().clearCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "Transaction created!", new TransactionDTO(transaction)));
    }

    @Transactional
    public ResponseEntity<ResponseObject> updateTransaction(Long id, TransactionRequestDTO transactionRequest) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found transaction with id:" + id));

        Voucher voucher = voucherRepository.findById(transactionRequest.getVoucherID())
                .orElseThrow(() -> new ResourceNotFoundException("Voucher not found with id: "+transactionRequest.getVoucherID()));

        //transaction.setUser(transactionRequest.getCart().getUser());
        transaction.setPaymentType(transactionRequest.getPaymentType());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setVoucher(voucher);
        transaction.setTotalPrice(transactionRequest.getTotalPrice());
        //transaction.setCart(transactionRequest.getCart());
        //transaction.setShipment(transactionRequest.getShipment());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update transaction successfully", new TransactionDTO(transactionRepository.save(transaction))));
    }

    @Transactional
    public ResponseEntity<ResponseObject> updatePayment(Long id, TransactionStatus transactionStatus) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found transaction with id:" + id));
        transaction.setStatus(transactionStatus);
        transactionRepository.save(transaction);
        return ResponseEntity.ok(new ResponseObject("ok", "Transaction status updated!", new TransactionDTO(transactionRepository.save(transaction))));
    }

}
