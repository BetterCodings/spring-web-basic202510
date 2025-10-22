package com.codeit.springwebbasic.rental.repository;

import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.rental.entity.Rental;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class RentalRepository {
    private final Map<Long, Rental> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Rental save(Rental rental) {
        if (rental.getId() == null) {
            rental.setId(sequence.getAndIncrement());
        }
        store.put(rental.getId(), rental);
        return rental;
    }

    public Optional<Rental> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Rental> findAll() {
        return new ArrayList<>(store.values());
    }

    public List<Rental> findByMember(Member member) {
        return store.values().stream()
                .filter(rental -> rental.getMember().getId().equals(member.getId()))
                .collect(Collectors.toList());
    }

    public List<Rental> findActiveRentals() {
        return store.values().stream()
                .filter(rental -> rental.getReturnedAt() == null)
                .collect(Collectors.toList());
    }

    public List<Rental> findOverdueRentals() {
        return store.values().stream()
                .filter(rental -> rental.getReturnedAt() == null && rental.isOverdue())
                .collect(Collectors.toList());
    }

    public long countByMember(Member member) {
        return store.values().stream()
                .filter(rental -> rental.getMember().getId().equals(member.getId()))
                .count();
    }


}
