create table companies
(
    id               serial primary key,
    name             varchar(256) not null,
    cnpj             varchar(20),
    ipo              integer,
    foundation_year  integer,
    firm_value       bigint,
    number_of_papers bigint,
    market_segment   varchar(256),
    sector           varchar(256),
    segment          varchar(256),
    external_id      varchar(256)
);

create table balance_sheets
(
    id            serial primary key,
    company_id    bigint,
    year          integer not null,
    quarter       integer not null,
    net_revenue   bigint,
    costs         bigint,
    gross_profit  bigint,
    net_profit    bigint,
    ebitda        bigint,
    ebit          bigint,
    taxes         bigint,
    gross_debt    bigint,
    net_debt      bigint,
    equity        bigint,
    cash          bigint,
    assets        bigint,
    liabilities   bigint,
    constraint balance_sheets_company_id_companies_id_fk foreign key (company_id) references companies(id)
);

create table stocks
(
    id                  serial primary key,
    external_id         varchar(256),
    ticker              varchar(7) not null,
    company_id          bigint,
    free_float          numeric,
    tag_along           numeric,
    avg_daily_liquidity bigint,
    constraint stocks_company_id_companies_id_fk foreign key (company_id) references companies(id)
);

create table dividends
(
    id             serial primary key,
    stock_id       bigint,
    type           varchar(100) default 'Dividendo'::character varying not null,
    value          numeric not null,
    ownership_date date not null,
    payment_date   date not null,
    constraint dividends_stock_id_stocks_id_fk foreign key (stock_id) references stocks(id)
);

create table prices
(
    id         serial primary key,
    stock_id   bigint,
    value      numeric not null,
    price_date date not null,
    constraint prices_stock_id_stocks_id_fk foreign key (stock_id) references stocks(id)
);

-- INSERTS
INSERT INTO companies (id, name, cnpj, ipo, foundation_year, firm_value, number_of_papers, market_segment, sector, segment, external_id)
VALUES (1, 'PETROLEO BRASILEIRO S.A. PETROBRAS', '33000167000101', 1977, 1953, 754871726000, 13044496000, 'Nível 2', 'Empresas do Setor Petróleo, Gás e Biocombustíveis', 'Empresas do Segmento Exploração  Refino e Distribuição', null);

INSERT INTO stocks (id, external_id, ticker, company_id, free_float, tag_along, avg_daily_liquidity)
VALUES (4, null, 'petr4', 1, 63.33, 100, 1617781000);

INSERT INTO dividends (id, stock_id, type, value, ownership_date, payment_date)
VALUES (1, 4, 'Dividendos', 0.54947422, '2024-04-25', CURRENT_DATE);

INSERT INTO prices (id, stock_id, value, price_date)
VALUES (2, 4, 34.87, '2024-04-23'),
       (3, 4, 32.45, '2024-03-24'),
       (4, 4, 30.96, '2024-03-25'),
       (5, 4, 27.5, '2023-04-25'),
       (6, 4, 35.96, '2024-04-24'),
       (7, 4, 36.02, '2024-04-24'),
       (8, 4, 27.96, '2023-04-24');

INSERT INTO balance_sheets (id, company_id, year, quarter, net_revenue, costs, gross_profit, net_profit, ebitda, ebit, taxes, gross_debt, net_debt, equity, cash, assets, liabilities)
VALUES (1, 1, 2023, 1, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (2, 1, 2023, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (3, 1, 2018, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (4, 1, 2017, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (5, 1, 2019, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (6, 1, 2020, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000),
       (7, 1, 2021, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000, 303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000);

