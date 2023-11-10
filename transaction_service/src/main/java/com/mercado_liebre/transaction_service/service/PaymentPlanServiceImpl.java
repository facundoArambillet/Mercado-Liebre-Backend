package com.mercado_liebre.transaction_service.service;

import com.mercado_liebre.transaction_service.error.ResponseException;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentMapper;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlan;
import com.mercado_liebre.transaction_service.model.paymentPlant.PaymentPlanDTO;
import com.mercado_liebre.transaction_service.repository.PaymentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentPlanServiceImpl implements PaymentPlanService{
    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    public List<PaymentPlanDTO> getAll() {
        try {
            List<PaymentPlan> paymentPlans = paymentPlanRepository.findAll();
            List<PaymentPlanDTO> PaymentPlanDTOs = paymentPlans.stream().map(
                    paymentPlan -> PaymentMapper.mapper.paymentPlanToPaymentPlanDto(paymentPlan)).collect(Collectors.toList());

            return PaymentPlanDTOs;
        } catch (Exception e) {
            throw new ResponseException("Fail getAll", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<PaymentPlan> getById(Long idPayment) {
        try {
            Optional<PaymentPlan> paymentPlanFound = paymentPlanRepository.findById(idPayment);
            if (paymentPlanFound.isPresent()) {
                return paymentPlanFound;
            } else {
                throw new ResponseException("Payment plan not found", null,  HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Error occurred while fetching Payment plant", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public PaymentPlan createPaymentPlan(PaymentPlan paymentPlan) {
        try {
            Optional<PaymentPlan> paymentPlanFound = paymentPlanRepository.findByInstallments(paymentPlan.getInstallments());
            if(paymentPlanFound.isPresent()) {
                throw new ResponseException("Payment plan already exist", null , HttpStatus.NOT_FOUND);
            } else {
                return paymentPlanRepository.save(paymentPlan);
            }

        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Create PaymentPlan", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public PaymentPlanDTO updatePaymentPlan(Long idPayment, PaymentPlanDTO paymentPlanDTO) {
        try {
            Optional<PaymentPlan> paymentPlanFound = paymentPlanRepository.findById(idPayment);
            if(paymentPlanFound.isPresent()) {
                PaymentPlan paymentPlan = paymentPlanFound.get();
                paymentPlan.setInstallments(paymentPlanDTO.getInstallments());
                paymentPlan.setInterest(paymentPlanDTO.getInterest());
                paymentPlanRepository.save(paymentPlan);

                PaymentPlanDTO paymentPlanDTOUpdated = PaymentMapper.mapper.paymentPlanToPaymentPlanDto(paymentPlan);
                return paymentPlanDTOUpdated;
            } else {
                throw new ResponseException("That Payment plan does not exist ", null, HttpStatus.NOT_FOUND);
            }
        }  catch (ResponseException ex) {
            throw ex;
        }  catch (Exception e) {
            throw new ResponseException("Update PaymentPlan", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public PaymentPlanDTO deletePaymentPlan(Long idPayment) {
        try {
            Optional<PaymentPlan> paymentPlanFound = paymentPlanRepository.findById(idPayment);
            if(paymentPlanFound.isPresent()) {
                PaymentPlan paymentPlanDelete = paymentPlanFound.get();
                PaymentPlanDTO paymentPlanDTO = PaymentMapper.mapper.paymentPlanToPaymentPlanDto(paymentPlanDelete);
                paymentPlanRepository.delete(paymentPlanDelete);

                return paymentPlanDTO;
            } else {
                throw new ResponseException("That Payment plan does not exist", null, HttpStatus.NOT_FOUND);
            }
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseException("Delete PaymentPlan", e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
