
create table chemical_search
(
    id                  integer  primary key,
    dtxsid              varchar(20),
    dtxcid              varchar(20),
    casrn               varchar(255),
    smiles              text,
    preferred_name      varchar(255),
    search_group        varchar(50),
    search_name         varchar(50),
    search_value        text,
    modified_value      text,
    rank                integer,
    has_structure_image integer,
    is_markush          integer
);

create table chemical_lists
(
    id                 integer primary key,
    name               varchar(255),
    visibility         varchar(255),
    is_visible         boolean,
    short_description  text,
    long_description   text,
    created_date       timestamp,
    last_modified_date timestamp,
    created_by         varchar(255),
    chemical_count     integer,
    created_at         timestamp default now(),
    label              varchar(255),
    type               varchar(100)
);

create table chemical_list_details
(
    id                      integer primary key,
    dtxsid                  varchar(50),
    casrn                   varchar(255),
    compound_id             integer,
    generic_substance_id    integer,
    preferred_name          text,
    active_assays           integer,
    cpdata_count            integer,
    mol_formula             varchar(255),
    mol_weight              numeric,
    monoisotopicmass        numeric,
    percent_assays          integer,
    pubchem_count           integer,
    pubmed_count            numeric,
    qc_level                integer,
    qc_level_desc           varchar(255),
    stereo                  varchar(1),
    total_assays            integer,
    toxcast_select          text,
    pubchem_cid             integer,
    list_name               text,
    list_label              text,
    list_category           text,
    list_shortdesc          text,
    list_is_visible         boolean,
    list_visibility         text,
    sources_count           integer,
    multi_component         integer,
    isotope                 integer,
    has_structure_image     boolean,
    related_substance_count integer,
    related_structure_count integer,
    inchi                   text,
    inchi_key               text,
    iupac_name              text,
    smiles                  text
);

create table chemical_properties
(
    id          integer primary key,
    dtxsid      varchar(45),
    dtxcid      varchar(45),
    prop_type   text,
    unit        varchar(255),
    name        varchar(255),
    prop_value       double precision,
    source      varchar(255),
    description varchar(1024)
);

create table fate
(
    id            integer primary key,
    dtxsid        varchar(45),
    dtxcid        varchar(45),
    endpoint_name varchar(255),
    result_value  double precision,
    unit          varchar(255),
    max_value     double precision,
    min_value     double precision,
    model_source  varchar(255),
    description   varchar(1024),
    value_type    text
);

