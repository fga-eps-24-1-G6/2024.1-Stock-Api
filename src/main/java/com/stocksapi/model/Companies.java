package com.stocksapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "companies", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Companies {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "ipo")
    private Integer ipo;

    @Column(name = "foundation_year")
    private Integer foundationYear;

    @Column(name = "firm_value")
    private Long firmValue;

    @Column(name = "number_of_papers")
    private Long numberOfPapers;

    @Column(name = "market_segment")
    private String marketSegment;

    @Column(name = "sector")
    private String sector;

    @Column(name = "segment")
    private String segment;
}
