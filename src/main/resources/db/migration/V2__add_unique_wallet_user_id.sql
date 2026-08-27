alter table wallets
    add constraint uq_wallets_user_id unique (user_id);
