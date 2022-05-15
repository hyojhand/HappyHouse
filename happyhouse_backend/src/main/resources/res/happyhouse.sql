-- 외래키 이름 나중에
-- 외래키 설정 on update, on delete 나중에
-- apt는 있던 테이블 그대로
-- isdeleted는 실제로 디비에서 데이터를 삭제하지 않고 사용자에게만 보여주지 않기 위함 (데이터 백업용, 0이면 존재 1이면 삭제)

CREATE TABLE BOARD (
	articleno 	int 			primary key 	auto_increment,
    writer 		varchar(20) 	not null,
    title	 	varchar(200) 	not null,
    content 	varchar(4000) 	not null,
    likey 		int 			default 0,
    regtime 	timestamp 		default current_timestamp,
    isdeleted 	int 			default 0,
    constraint fk_board_writer foreign key (writer) references USER(userid)
);

CREATE TABLE USER (
	userid 		varchar(20) 	primary key,
    username 	varchar(20) 	not null,
    userpwd 	varchar(30) 	not null,
    email 		varchar(30) 	not null,
    joindate 	timestamp 		default current_timestamp,
    isadmin		int				default 0
);

CREATE TABLE REPLY (
	replyid 	int 			primary key 	auto_increment,
    articleno	int				not null,
	writer 		varchar(20)		not null,
    content 	varchar(1000) 	not null,
    regtime 	timestamp 		default current_timestamp,
    isdeleted 	int 			default 0,
    constraint fk_reply_writer foreign key (writer) references USER(userid)
);

CREATE TABLE LIKEY (
	userid 		varchar(20),
    articleno	int,
    primary key (userid, articleno),
    constraint fk_likey_userid foreign key (userid) references USER(userid),
    constraint fk_likey_articleno foreign key (articleno) references BOARD(articleno)
);

CREATE TABLE MESSAGE (
	messageid	int				primary key 	auto_increment,
    send 		varchar(20) 	not null,
    receive 	varchar(20) 	not null,
    title 		varchar(50) 	not null,
    content 	varchar(500) 	not null,
    regtime 	timestamp 		default current_timestamp,
    isdeleted	int				default 0,
    constraint fk_message_send foreign key (send) references USER(userid),
    constraint fk_message_receive foreign key (receive) references USER(userid)
);