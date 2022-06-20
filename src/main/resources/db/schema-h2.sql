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