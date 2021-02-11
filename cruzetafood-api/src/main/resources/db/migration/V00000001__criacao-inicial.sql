 CREATE TABLE cidade
  (
     id        BIGINT NOT NULL auto_increment,
     nome      VARCHAR(255) NOT NULL,
     estado_id BIGINT NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE cozinha
  (
     id   BIGINT NOT NULL auto_increment,
     nome VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE estado
  (
     id   BIGINT NOT NULL auto_increment,
     nome VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE forma_pagamento
  (
     id        BIGINT NOT NULL auto_increment,
     descricao VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE grupo
  (
     id   BIGINT NOT NULL auto_increment,
     nome VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE grupo_permissao
  (
     grupo_id     BIGINT NOT NULL,
     permissao_id BIGINT NOT NULL
  )
engine=innodb;

CREATE TABLE hibernate_sequence
  (
     next_val BIGINT
  )
engine=innodb;

CREATE TABLE permissao
  (
     id        BIGINT NOT NULL auto_increment,
     descricao VARCHAR(255) NOT NULL,
     nome      VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE produto
  (
     id             BIGINT NOT NULL auto_increment,
     alvo           BIT,
     descricao      VARCHAR(255) NOT NULL,
     preco          DECIMAL(19, 2) NOT NULL,
     restaurante_id BIGINT,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE restaurante
  (
     id                   BIGINT NOT NULL auto_increment,
     data_atualizacao     DATETIME NOT NULL,
     data_cadastro        DATETIME NOT NULL,
     endereco_bairro      VARCHAR(255),
     endereco_cep         VARCHAR(255),
     endereco_complemento VARCHAR(255),
     endereco_logradouro  VARCHAR(255),
     endereco_numero      VARCHAR(255),
     nome                 VARCHAR(255) NOT NULL,
     taxa_frete           DECIMAL(19, 2) NOT NULL,
     cozinha_id           BIGINT NOT NULL,
     endereco_cidade_id   BIGINT,
     PRIMARY KEY (id)
  )
engine=innodb;

CREATE TABLE restaurante_forma_pagamento
  (
     restaurante_id     BIGINT NOT NULL,
     forma_pagamento_id BIGINT NOT NULL
  )
engine=innodb;

CREATE TABLE restaurante_produto
  (
     restaurante_id BIGINT NOT NULL,
     produto_id     BIGINT NOT NULL
  )
engine=innodb;

CREATE TABLE usuario 
  (
    id            BIGINT NOT NULL, 
    data_cadastro DATETIME(6) NOT NULL, 
    email         VARCHAR(255) NOT NULL, 
    nome          VARCHAR(255) NOT NULL, 
    senha         VARCHAR(255) NOT NULL, 
    PRIMARY KEY (id)
  ) 
engine=InnoDB;

CREATE TABLE usuario_grupo
  (
     usuario_id BIGINT NOT NULL,
     grupo_id   BIGINT NOT NULL
  )
engine=innodb;

ALTER TABLE cidade
  ADD CONSTRAINT fkkworrwk40xj58kevvh3evi500 FOREIGN KEY (estado_id) REFERENCES
  estado (id);

ALTER TABLE grupo_permissao
  ADD CONSTRAINT fkh21kiw0y0hxg6birmdf2ef6vy FOREIGN KEY (permissao_id)
  REFERENCES permissao (id);

ALTER TABLE grupo_permissao
  ADD CONSTRAINT fkta4si8vh3f4jo3bsslvkscc2m FOREIGN KEY (grupo_id) REFERENCES
  grupo (id);

ALTER TABLE produto
  ADD CONSTRAINT fkb9jhjyghjcn25guim7q4pt8qx FOREIGN KEY (restaurante_id)
  REFERENCES restaurante (id);

ALTER TABLE restaurante
  ADD CONSTRAINT fk76grk4roudh659skcgbnanthi FOREIGN KEY (cozinha_id) REFERENCES
  cozinha (id);

ALTER TABLE restaurante
  ADD CONSTRAINT fkbc0tm7hnvc96d8e7e2ulb05yw FOREIGN KEY (endereco_cidade_id)
  REFERENCES cidade (id);

ALTER TABLE restaurante_forma_pagamento
  ADD CONSTRAINT fk7aln770m80358y4olr03hyhh2 FOREIGN KEY (forma_pagamento_id)
  REFERENCES forma_pagamento (id);

ALTER TABLE restaurante_forma_pagamento
  ADD CONSTRAINT fka30vowfejemkw7whjvr8pryvj FOREIGN KEY (restaurante_id)
  REFERENCES restaurante (id);

ALTER TABLE restaurante_produto
  ADD CONSTRAINT fkoknuvhwlahk5m1tmb6bk5tpx9 FOREIGN KEY (produto_id) REFERENCES
  produto (id);

ALTER TABLE restaurante_produto
  ADD CONSTRAINT fkp1hdyuf6s9lcenhkafnno3s1h FOREIGN KEY (restaurante_id)
  REFERENCES restaurante (id);

ALTER TABLE usuario_grupo
  ADD CONSTRAINT fkk30suuy31cq5u36m9am4om9ju FOREIGN KEY (grupo_id) REFERENCES
  grupo (id);

ALTER TABLE usuario_grupo
  ADD CONSTRAINT fkdofo9es0esuiahyw2q467crxw FOREIGN KEY (usuario_id) REFERENCES
  usuario (id);  