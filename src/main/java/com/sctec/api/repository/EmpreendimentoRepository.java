package com.sctec.api.repository;

import com.sctec.api.entity.Empreendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpreendimentoRepository extends JpaRepository<Empreendimento, Long> {
}
