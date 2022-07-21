CREATE TABLE IF NOT EXISTS trade_info
(
    id           integer      NOT NULL auto_increment,
    base_id      integer      NOT NULL,
    base_symbol  VARCHAR(100) NOT NULL,
    quote_id     integer      NOT NULL,
    quote_symbol VARCHAR(100) NOT NULL,
    sale_or_buy  VARCHAR(10)  NOT NULL,
    price        VARCHAR(100) NOT NULL,
    base_num     VARCHAR(100) NOT NULL,
    quote_num    VARCHAR(100) NOT NULL,
    trade_date   VARCHAR(100) NOT NULL,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS coinMarketCap_id_map
(
    id integer NOT NULL,
    name VARCHAR(100) NOT NULL,
    symbol VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL,
    is_active integer NOT NULL,
    rank integer NOT NULL,
    first_historical_data VARCHAR(100),
    last_historical_data VARCHAR(100),
    platform_id integer,
    token_address VARCHAR(100),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS coinMarketCap_listings  (
    id integer NOT NULL,
    name VARCHAR(100),
    symbol VARCHAR(100),
    slug VARCHAR(100),
    cmc_rank integer,
    date_added VARCHAR(100),
    platform_id integer,
    token_address VARCHAR(100),
    num_market_pairs integer,
    max_supply VARCHAR(100),
    circulating_supply VARCHAR(100),
    total_supply VARCHAR(100),
    price VARCHAR(100),
    volume_24h VARCHAR(100),
    volume_change_24h VARCHAR(100),
    percent_change_1h VARCHAR(100),
    percent_change_24h VARCHAR(100),
    percent_change_7d VARCHAR(100),
    market_cap VARCHAR(100),
    last_updated VARCHAR(100),
    primary key (id)
);

CREATE TABLE IF NOT EXISTS CMCQuotesLatest
(
    id integer NOT NULL,
    name VARCHAR(100),
    symbol VARCHAR(100),
    slug VARCHAR(100),
    num_market_pairs integer,
    date_added VARCHAR(100),
    max_supply VARCHAR(100),
    circulating_supply VARCHAR(100),
    total_supply VARCHAR(100),
    is_active integer,
    platform_id integer,
    token_address VARCHAR(100),
    cmc_rank integer,
    last_updated VARCHAR(100),
    price VARCHAR(100),
    volume_24h VARCHAR(100),
    volume_change_24h VARCHAR(100),
    percent_change_1h VARCHAR(100),
    percent_change_24h VARCHAR(100),
    percent_change_7d VARCHAR(100),
    percent_change_30d VARCHAR(100),
    percent_change_60d VARCHAR(100),
    percent_change_90d VARCHAR(100),
    market_cap VARCHAR(100),
    market_cap_dominance VARCHAR(100),
    primary key (id)
);