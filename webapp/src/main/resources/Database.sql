use ccweb;
create table Users
(
    email           varchar(128)  not null,
    password        varchar(256) not null,
    firstName       varchar(100)     null,
    lastName        varchar(100)     null,
    account_created varchar(100)     null,
    account_updated varchar(100)     null,
    id              varchar(256) not null ,
    constraint users_pk
        unique (id)
);

create table Recipie
(
    id                    varchar(256) not null primary key unique ,
    created_ts            varchar(100) null,
    updated_ts            varchar(100) null,
    author_id             varchar(256) null,
    cook_time_in_min      int       null,
    prep_time_in_min      int       null,
    total_time_in_min     int       null,
    title                 varchar(100) null,
    cusine                varchar(100) null,
    servings              int       null
);

create table Steps
(
    position                    int not null,
    items                       varchar(256) null,
    recipie_id                  varchar(256) not null
);



create table NutritionInformation
(
    recipie_id             varchar(256) not null,
    calories               int   null,
    cholesterol_in_mg      float null,
    sodium_in_mg           int   null,
    carbohydrates_in_grams float null,
    protein_in_grams       float null
    --foreign key(recipie_id) references Recipie(id);
);

create table Image
(
    id varchar(256) not null,
    url nvarchar(1024) null,
    --foreign key (id) references Recipie(id)

);

create table Ingredients
(
    recipie_id varchar(256) ,
    content varchar(256)
)