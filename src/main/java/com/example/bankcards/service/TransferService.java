package com.example.bankcards.service;

import com.example.bankcards.dto.TransferRequestDto;
import com.example.bankcards.dto.TransferResponseDto;
import com.example.bankcards.entity.*;
import com.example.bankcards.exception.BadRequestException;
import com.example.bankcards.exception.ForbiddenException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;


    @Transactional
    public TransferResponseDto transfer(UserDetails userDetails, TransferRequestDto dto) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found!"));

        if (dto.amount().signum() <= 0)
            throw new BadRequestException("Amount must be positive");

        Card from = cardRepository.findById(dto.fromCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));
        Card to = cardRepository.findById(dto.toCardId())
                .orElseThrow(() -> new NotFoundException("Card not found"));

        if (!from.getOwner().getId().equals(user.getId()) ||
                !to.getOwner().getId().equals(user.getId()))
            throw new ForbiddenException(("Cards must belong to user"));

        if (from.getStatus() != CardStatus.ACTIVE ||
                to.getStatus() != CardStatus.ACTIVE)
            throw new BadRequestException("Card not active");

        if (from.getBalance().compareTo(dto.amount()) < 0)
            throw new BadRequestException("Insufficient funds");

        from.setBalance(from.getBalance().subtract(dto.amount()));
        to.setBalance(to.getBalance().add(dto.amount()));

        Transfer transfer = new Transfer();
        transfer.setFromCard(from);
        transfer.setToCard(to);
        transfer.setAmount(dto.amount());
        transfer.setStatus(TransferStatus.SUCCESS);

        transferRepository.save(transfer);
        return toDto(transfer);
    }

    private TransferResponseDto toDto(Transfer transfer) {
        return  new TransferResponseDto(
                transfer.getFromCard().getId(),
                transfer.getToCard().getId(),
                transfer.getAmount(),
                transfer.getStatus(),
                transfer.getFailureReason(),
                transfer.getCreatedAt()
        );

    }
}
