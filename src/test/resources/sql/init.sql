create table if not exists companies
(
    id               serial
    primary key,
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

create table if not exists balance_sheets
(
    company_id   serial
        constraint balance_sheets_company_id_companies_id_fk
            references companies,
    year         integer not null,
    quarter      integer not null,
    net_revenue  bigint,
    costs        bigint,
    gross_profit bigint,
    net_profit   bigint,
    ebitda       bigint,
    ebit         bigint,
    taxes        bigint,
    gross_debt   bigint,
    net_debt     bigint,
    equity       bigint,
    cash         bigint,
    assets       bigint,
    liabilities  bigint,
    id           serial
);

create index if not exists balance_sheets_company_idx
    on balance_sheets (company_id);

create index if not exists balance_sheets_year_idx
    on balance_sheets (year);

create table if not exists stocks
(
    id                  serial
        primary key,
    external_id         varchar(256),
    ticker              varchar(7) not null,
    company_id          serial
        constraint stocks_company_id_companies_id_fk
            references companies,
    free_float          numeric,
    tag_along           numeric,
    avg_daily_liquidity bigint
);

create table if not exists dividends
(
    stock_id       serial
        constraint dividends_stock_id_stocks_id_fk
            references stocks,
    type           varchar(100) default 'Dividendo'::character varying not null,
    value          numeric                                             not null,
    ownership_date date                                                not null,
    payment_date   date                                                not null,
    id             serial
);

create index if not exists dividends_stock_idx
    on dividends (stock_id);

create table if not exists prices
(
    stock_id   serial
        constraint prices_stock_id_stocks_id_fk
            references stocks,
    value      numeric not null,
    price_date date    not null,
    id         serial
        primary key
);

create index if not exists prices_stock_idx
    on prices (stock_id);

INSERT INTO public.companies (id, name, cnpj, ipo, foundation_year, firm_value, number_of_papers, market_segment,
                              sector, segment, external_id)
SELECT * FROM (VALUES
                   (1, 'PETROLEO BRASILEIRO S.A. PETROBRAS', '33000167000101', 1977, 1953, 754871726000, 13044496000, 'Nível 2',
                    'Empresas do Setor Petróleo, Gás e Biocombustíveis', 'Empresas do Segmento Exploração  Refino e Distribuição',
                    null)
              ) AS new_company
WHERE NOT EXISTS (
    SELECT 1 FROM public.companies WHERE id = 1
);

INSERT INTO public.stocks (id, external_id, ticker, company_id, free_float, tag_along, avg_daily_liquidity)
SELECT * FROM (VALUES
                   (4, null, 'petr4', 1, 63.33, 100, 1617781000)
              ) AS new_stock
WHERE NOT EXISTS (
    SELECT 1 FROM public.stocks WHERE id = 4
);

INSERT INTO public.dividends (stock_id, type, value, ownership_date, payment_date, id)
SELECT * FROM (VALUES
                   (4, 'Dividendos', 0.54947422, '2024-04-25'::date, '2024-05-20'::date, 1)
              ) AS new_dividend
WHERE NOT EXISTS (
    SELECT 1 FROM public.dividends WHERE id = 1
);

INSERT INTO public.prices (stock_id, value, price_date, id)
SELECT new_price.*
FROM (
         VALUES
             (4, 34.87, '2024-04-23'::date, 2),
             (4, 32.45, '2024-03-24'::date, 5),
             (4, 30.96, '2024-03-25'::date, 6),
             (4, 27.5, '2023-04-25'::date, 7),
             (4, 35.96, '2024-04-24'::date, 3),
             (4, 36.02, '2024-04-24'::date, 4),
             (4, 27.96, '2023-04-24'::date, 8)
     ) AS new_price(stock_id, value, price_date, id)
WHERE NOT EXISTS (
    SELECT 1 FROM public.prices WHERE stock_id = new_price.stock_id AND price_date = new_price.price_date AND id = new_price.id
);

-- Verificar se o balanço patrimonial já existe antes de inserir
-- Verificar se o balanço patrimonial já existe antes de inserir
INSERT INTO public.balance_sheets (company_id, year, quarter, net_revenue, costs, gross_profit, net_profit, ebitda,
                                   ebit, taxes, gross_debt, net_debt, equity, cash, assets, liabilities, id)
SELECT new_balance_sheet.*
FROM (
         VALUES
             (1, 2023, 1, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000,
              303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000, 1),
             (1, 2023, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000,
              303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000, 2)
     ) AS new_balance_sheet(company_id, year, quarter, net_revenue, costs, gross_profit, net_profit, ebitda,
                            ebit, taxes, gross_debt, net_debt, equity, cash, assets, liabilities, id)
WHERE NOT EXISTS (
    SELECT 1 FROM public.balance_sheets WHERE company_id = new_balance_sheet.company_id AND year = new_balance_sheet.year
                                          AND quarter = new_balance_sheet.quarter AND id = new_balance_sheet.id
);
