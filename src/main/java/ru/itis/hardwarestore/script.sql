DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS order_lists;
DROP TABLE IF EXISTS cart_elements;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS discount_cards;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS card_types;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

CREATE TABLE users (
                       id UUID ,
                       role VARCHAR(8) NOT NULL ,
                       first_name VARCHAR(25) NOT NULL ,
                       last_name VARCHAR(25) NOT NULL ,
                       password_hash TEXT NOT NULL ,
                       salt TEXT NOT NULL ,
                       phone VARCHAR(12) NOT NULL ,
                       email VARCHAR(256) NOT NULL ,
                       birthday DATE ,
                       created_at TIMESTAMP ,
                       updated_at TIMESTAMP,
    ---------------------------------
                       CONSTRAINT users_id_pk PRIMARY KEY (id),
                       CONSTRAINT users_password_hash_uk UNIQUE (password_hash),
                       CONSTRAINT users_salt_uk UNIQUE (salt),
                       CONSTRAINT users_phone_uk UNIQUE (phone),
                       CONSTRAINT users_email_uk UNIQUE (email)
);

CREATE TABLE sessions (
                          session_id TEXT ,
                          user_id UUID NOT NULL ,
                          expire_at TIMESTAMP NOT NULL ,
    ------------------------------
                          CONSTRAINT sessions_sessions_id_pk PRIMARY KEY (session_id),
                          CONSTRAINT sessions_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE categories (
                            id SERIAL ,
                            name TEXT NOT NULL ,
                            parent_id INT ,
                            slug TEXT NOT NULL ,
                            image_url TEXT NOT NULL ,
                            created_at TIMESTAMP NOT NULL ,
                            updated_at TIMESTAMP NOT NULL ,
    ---------------------
                            CONSTRAINT categories_id_pk PRIMARY KEY (id) ,
                            CONSTRAINT categories_name_uk UNIQUE (name) ,
                            CONSTRAINT categories_parent_id_fk FOREIGN KEY (parent_id) REFERENCES categories(id) ,
                            CONSTRAINT categories_slug_uk UNIQUE (slug)
);

CREATE TABLE products (
                          id SERIAL ,
                          name TEXT NOT NULL ,
                          slug TEXT NOT NULL ,
                          description TEXT NOT NULL ,
                          category_id INT ,
                          price_per_unit NUMERIC NOT NULL ,
                          quantity INT NOT NULL ,
                          image_url TEXT NOT NULL ,
                          created_at TIMESTAMP NOT NULL ,
                          updated_at TIMESTAMP NOT NULL ,
    ---------------------------------
                          CONSTRAINT products_id_pk PRIMARY KEY (id) ,
                          CONSTRAINT products_name_uk UNIQUE (name) ,
                          CONSTRAINT products_slug_uk UNIQUE (slug) ,
                          CONSTRAINT products_category_id FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE card_types (
                            id SMALLINT ,
                            name VARCHAR(16) NOT NULL ,
                            discount_percent INT NOT NULL ,
    ------------------------------------
                            CONSTRAINT card_types_id_pk PRIMARY KEY (id)
);

CREATE TABLE discount_cards (
                                id TEXT ,
                                user_id UUID NOT NULL ,
                                card_no VARCHAR(12) NOT NULL ,
                                card_type_id INT NOT NULL ,
                                status VARCHAR(12) DEFAULT 'INACTIVE',
                                created_at TIMESTAMP NOT NULL ,
                                updated_at TIMESTAMP NOT NULL ,
    --------------------------------------
                                CONSTRAINT discount_cards_id_pk PRIMARY KEY (id),
                                CONSTRAINT discount_cards_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id),
                                CONSTRAINT discount_cards_card_no_uk UNIQUE (card_no),
                                CONSTRAINT discount_cards_card_type_id_fk FOREIGN KEY (card_type_id) REFERENCES card_types(id)
);

CREATE TABLE cart_elements (
                               id SERIAL ,
                               user_id UUID NOT NULL ,
                               product_id INT NOT NULL ,
                               quantity INT NOT NULL,
    -------------------------
                               CONSTRAINT cart_elements_id_pk PRIMARY KEY (id),
                               CONSTRAINT cart_elements_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id),
                               CONSTRAINT cart_elements_product_id_fk FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT cart_elements_user_and_product_id_uk UNIQUE (user_id, product_id)
);

CREATE TABLE orders (
                        id SERIAL ,
                        user_id UUID ,
                        amount_before_discount NUMERIC NOT NULL ,
                        discount_amount NUMERIC NOT NULL ,
                        total_amount NUMERIC NOT NULL ,
                        created_at TIMESTAMP NOT NULL ,
                        delivered_at TIMESTAMP ,
                        closed_at TIMESTAMP ,
    -----------------------------------------
                        CONSTRAINT orders_id_pk PRIMARY KEY (id),
                        CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_lists (
                             order_id INT ,
                             product_id INT ,
                             quantity INT NOT NULL ,
    -----------------------
                             CONSTRAINT order_lists_order_id_fk FOREIGN KEY (order_id) REFERENCES orders(id),
                             CONSTRAINT order_lists_product_id_fk FOREIGN KEY (product_id) REFERENCES products(id),
                             CONSTRAINT order_lists_order_id_and_product_id_uk UNIQUE (order_id, product_id)
);


INSERT INTO
    card_types (id, name, discount_percent)
VALUES (1, 'SILVER', 5),
       (2, 'GOLDEN', 7),
       (3, 'PLATINUM', 10);

INSERT INTO categories(name, parent_id, slug, image_url, created_at, updated_at)
VALUES ('Сухие смеси', null, 'sukhie-smesi', '/images/categories/sukhiesmesi.jpg', now(), null),
       ('Шпатлёвка', 1, 'shpatlevka', '/images/categories/shpatlevka.jpg', now(), null),
       ('Штукатурка', 1, 'shtukaturka','/images/categories/shtukaturka.jpg', now(), null),
       ('Эмали', null, 'emali', '/images/categories/emali.jpg', now(), null),
       ('Эмали по ржавчине', 4, 'emali-po-rzhavchine', '/images/categories/po-rzhavchine.jpg', now(), null),
       ('Акриловые эмали', 4, 'emali-akrilovie', '/images/categories/akrilovie.jpg', now(), null),
       ('Кисти', null, 'kisti', '/images/categories/kisti.jpg', now(), null);

INSERT INTO products(name, slug, description, category_id, price_per_unit, quantity, image_url, created_at, updated_at)
VALUES ('Шпатлёвка полимерная VETONIT 20кг', 'shpatlevka-polimernaya-vetonit-20-kg',
        'Шпаклевка VETONIT L предназначена для финишной отделки стен в сухих помещениях. Она идеально подходит для подготовки поверхностей под покраску или поклейку обоев. Шпаклевка обладает белым цветом, что облегчает последующую покраску стен.
        Однако, важно помнить, что Vetonit L не является водостойкой и не подходит для выравнивания полов или оснований под облицовку плиткой. Она также не предназначена для использования в помещениях с повышенной влажностью.
        После нанесения и шлифовки шпаклевки поверхность должна соответствовать качеству не ниже К2 согласно СП 71.1330 2017. Это означает, что поверхность должна быть гладкой, без видимых дефектов и готова к последующей отделке.',
        2, 720.00, 1200, '/images/sukhie-smesi/shpatlevka/vetonit20kg.jpg', now(), null),
       ('Шпатлёвка гипсовая ВОЛМА Финиш 20кг', 'shpatlevka-gipsovaya-volma-finish-20-kg',
        '«ВОЛМА-Стандарт» - сухая шпаклевочная смесь на основе гипсового вяжущего, минеральных наполнителей и модифицирующих добавок, обеспечивающих трещиностойкость и повышенную адгезию.
       Для базового выравнивания стен и потолков внутри сухих помещений любого назначения под оклейку обоями, покраску и других видов декоративной отделки.
       Виды оснований: бетонные, гипсовые, пено- и газобетонные, оштукатуренные поверхности, пазогребневые гипсовые плиты, гипсокартонные и гипсоволокнистые листы.',
        2, 469.99, 1000, '/images/sukhie-smesi/shpatlevka/volmafinish20kg.jpg', now(), null),
       ('Шпатлёвка гипсовая СТАРАТЕЛИ 20кг', 'shpatlevka-gipsovaya-starateli-20-kg',
        'Применяется для тонкослойного выравнивания стен и потолков внутри помещений с нормальным уровнем влажности с целью получения высококачественной финишной поверхности под последующую окраску, оклейку тонкими обоями и другие виды декоративных покрытий.',
        2, 520.00, 1000, '/images/sukhie-smesi/shpatlevka/starateli20kg.jpg', now(), null),
       ('Шпатлёвка ваниловая KNAUF Rotband 5кг', 'shpatlevka-vanilovaya-knauf-rotband-5-kg',
        'Готовая пастообразная финишная шпаклевка на виниловой основе.
       Обладает повышенной белизной, адгезией и трещиностойкостью. Идеально подходит для тонкослойного (на сдир) шпаклевания поверхности.
       Благодаря высокой пластичности легко разравнивается и отлично заполняет собой все мелкие неровности.
       После высыхания поверхность готова под финишное покрытие краской или обоями.',
        2, 1029.99, 500, '/images/sukhie-smesi/shpatlevka/rotband5kg.jpg', now(), null),
       ('Штукатурка гипсовая ВОЛМА Слой 30кг', 'shtukaturka-gipsovaya-volma-sloy-30-kg',
        'ВОЛМА-Слой - сухая штукатурная смесь на основе гипсового вяжущего, легкого заполнителя с применением минеральных и химических добавок, обеспечивающих высокую адгезию, водоудерживающую способность и оптимальное время работы.',
        3, 519.99, 2000, '/images/sukhie-smesi/shtukaturka/volmasloy30kg.jpg', now(), null),
       ('Штукатурка цементная UNIS Силин 25 кг', 'shtukaturka-cementnaya-unis-silin-25-kg',
        'Универсальная армированная цементная штукатурка СИЛИН УНИВЕРСАЛЬНЫЙ АРМИРОВАННЫЙ предназначена для выравнивания поверхностей стен слоем до 30 мм (без штукатурной сетки); для ремонта сколов, выбоин, раковин, трещин глубиной до 60 мм, заполнения стыков ЖБИ. Рекомендуется использовать для наружных работ, а также внутри сухих и влажных, отапливаемых и неотапливаемых помещений. Допускается использовать для возведения стен и перегородок из газосиликата, пенобетона, газобетона, силикатного и керамического кирпича. Используется для ручного и механизированного нанесения.',
        3, 600, 750, '/images/sukhie-smesi/shtukaturka/unissilin25kg.jpg', now(), null),
       ('Эмаль-грунт ЛАКРА по ржавчине 3 в 1 графит глянцевая 0,8 кг', 'grunt-emal-3-v-1-lakra-800-ml',
        'Грунт-эмаль 3 в 1 Лакра 0,8кг по ржавчине, гладкая.Высококачественная грунт-эмаль на алкидной основе, сочетает в себе свойства преобразователя ржавчины, антикоррозионного грунта и декоративной эмали.После высыхания образует глянцевое покрытие, устойчивое к механическим и атмосферным воздействиям.Для наружных и внутренних работ.',
        5, 561.99, 400, '/images/emali/po-rzhavchine/lakra3v1ml800.jpg', now(), null),
       ('Эмаль акриловая универсальная ЛАКРА PROF IT матовая база А 0,9 кг', 'emal-akrilovaya-lakra-profi-900-ml',
        'Высококачественная водоразбавляемая эмаль на основе акриловой дисперсии для внутренних и наружных работ. Предназначена для окраски минеральных и деревянных поверхностей, не подвергающихся плотному соприкосновению (отсутствие последующего контакта окрашенных поверхностей между собой). Обладает отличной укрывистостью и адгезией к большинству строительных материалов. Может применяться как фасадная или интерьерная краска, в том числе для окрашивания обоев. Быстро высыхает. Создает прочное влагостойкое, атмосферостойкое, паропроницаемое и светостойкое покрытие. Не желтеет со временем. Отличается высокими декоративными и защитными свойствами, стойкая к мытью.',
        6, 605.99, 250, '/images/emali/akrilovie/lakraprofi900ml.jpg', now(), null),
       ('Кисть плоская KORVUS 50 мм натуральная щетина пластиковая ручка', 'kist-korvus-50-mm',
        'Плоская кисть KORVUS, натуральная щетина, пластиковая рукоятка имеет классическое исполнение и подойдет для окрашивания наружных и внутренних поверхностей. Применяется для нанесения красок, эмалей, лаков и других лакокрасочных материалов на различные поверхности. Пластиковая рукоятка оснащена отверстием для подвески. Натуральная щетина обеспечивает отличные краскообменные свойства кисти.',
        7, 44.00, 5000, '/images/kisti/korvus50mm.jpg', now(), null);