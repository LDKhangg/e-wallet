create table wallets (
    id uuid primary key,
    user_id uuid not null,
    balance numeric(19,0) not null check (balance >= 0),
    version bigint not null
);

create table transactions (
    id uuid primary key,
    from_wallet_id uuid not null,
    to_wallet_id uuid not null,
    amount numeric(19,0) not null check (amount > 0),
    status varchar(32) not null
);
