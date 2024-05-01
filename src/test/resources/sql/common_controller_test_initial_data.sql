INSERT INTO public.companies (id, name, cnpj, ipo, foundation_year, firm_value, number_of_papers, market_segment,
                              sector, segment, external_id)
VALUES (1, 'PETROLEO BRASILEIRO S.A. PETROBRAS', '33000167000101', 1977, 1953, 754871726000, 13044496000, 'Nível 2',
        'Empresas do Setor Petróleo, Gás e Biocombustíveis', 'Empresas do Segmento Exploração  Refino e Distribuição',
        null);

INSERT INTO public.stocks (id, external_id, ticker, company_id, free_float, tag_along, avg_daily_liquidity)
VALUES (4, null, '5', 1, 63.33, 100, 1617781000);


INSERT INTO public.dividends (stock_id, type, value, ownership_date, payment_date, id)
VALUES (4, 'Dividendos', 0.54947422, '2024-04-25', '2024-05-20', 1);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 34.87, '2024-04-23', 2);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 32.45, '2024-03-24', 5);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 30.96, '2024-03-25', 6);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 27.5, '2023-04-25', 7);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 35.96, '2024-04-24', 3);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 36.02, '2024-04-24', 4);

INSERT INTO public.prices (stock_id, value, price_date, id)
VALUES (4, 27.96, '2023-04-24', 8);

INSERT INTO public.balance_sheets (company_id, year, quarter, net_revenue, costs, gross_profit, net_profit, ebitda,
                                   ebit, taxes, gross_debt, net_debt, equity, cash, assets, liabilities, id)
VALUES (1, 2023, 1, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000,
        303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000, 1);

INSERT INTO public.balance_sheets (company_id, year, quarter, net_revenue, costs, gross_profit, net_profit, ebitda,
                                   ebit, taxes, gross_debt, net_debt, equity, cash, assets, liabilities, id)
VALUES (1, 2023, 4, 511994000000, -242061000000, 269933000000, 125166000000, 255546000000, 189342000000, -52315000000,
        303062000000, 227799000000, 382340000000, 75263000000, 1050888000000, 1050888000000, 2);