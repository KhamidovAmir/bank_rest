package com.example.bankcards.service;

import com.example.bankcards.dto.CardResponseDto;
import com.example.bankcards.dto.CardsPageRequestDto;
import com.example.bankcards.dto.CreateCardRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CardResponseDto createCard(CreateCardRequestDto dto) {
        User owner = userRepository.findById(dto.ownerId())
                .orElseThrow(() -> new NotFoundException("User not found"));


        String masked = getMaskedNumber();
        String encrypted = "Encrypted_" + masked;

        Card card = new Card();
        card.setOwner(owner);
        card.setBalance(dto.balance());
        card.setExpirationDate(dto.expirationDate());
        card.setStatus(CardStatus.ACTIVE);
        card.setEncryptedNumber(encrypted);
        card.setMaskedNumber(masked);

        cardRepository.save(card);
        return toDto(card);

    }

    public Page<CardResponseDto> getUserCards(Long userId, int page, int size) {
        return cardRepository.findByOwnerId(userId, PageRequest.of(page, size))
                .map(this::toDto);
    }

    public Page<CardResponseDto> getMyCards(UserDetails userDetails, int page, int size) {
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User thisUser = optionalUser.get();

        return cardRepository.findByOwnerId(thisUser.getId(), PageRequest.of(page, size))
                .map(this::toDto);
    }

    public void deleteCard(Long cardId){
        if(!cardRepository.existsById(cardId)){
            throw new NotFoundException("Card not found");
        }
        cardRepository.deleteById(cardId);
    }

    private String getMaskedNumber() {
        return "**** **** **** " +  generateCardNumber();
    }

    private String generateCardNumber() {
        Random rand = new Random();
        int number = rand.nextInt(1000, 9999);
        return String.valueOf(number);
    }


    private CardResponseDto toDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getMaskedNumber(),
                card.getExpirationDate(),
                card.getStatus(),
                card.getBalance()
        );
    }

}
