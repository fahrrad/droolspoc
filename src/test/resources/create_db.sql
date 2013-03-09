CREATE TABLE plant (id int primary key auto_increment 
, name varchar(255) NOT NULL unique
, name_parent_male varchar(255) NULL
, name_parent_female varchar(255) NULL
, rpd_nr varchar(63) NOT NULL
, transgene bit null
, zygosity char(2) null
, conform bit null
);

CREATE TABLE pcrresult (id int primary key auto_increment
, plant_id int
, pcr_target varchar(63) 
, is_test_gene_present bit
, copy_nr float
, is_used bit default 1

,foreign key( plant_id ) references plant
);

