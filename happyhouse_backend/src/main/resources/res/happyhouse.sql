CREATE TABLE USER (
	userid 		varchar(20) 	primary key,
    username 	varchar(20) 	not null,
    userpwd 	varchar(64) 	not null,
    email 		varchar(30) 	not null,
    joindate 	timestamp 		default current_timestamp,
    isadmin		int				default 0
);

# 일반 유저 계정
INSERT INTO USER(userid, username, userpwd, email, isadmin) VALUES ('ssafy', '김싸피', SHA2('1234', 256), 'ssafy@ssafy.com', 0);
INSERT INTO USER(userid, username, userpwd, email, isadmin) VALUES ('son', '손싸피', SHA2('1234', 256), 'son@ssafy.com', 0);
INSERT INTO USER(userid, username, userpwd, email, isadmin) VALUES ('choi', '최싸피', SHA2('1234', 256), 'choi@ssafy.com', 0);
INSERT INTO USER(userid, username, userpwd, email, isadmin) VALUES ('kim', '싸피김', SHA2('1234', 256), 'kim@ssafy.com', 0);
# 관리자 계정
INSERT INTO USER(userid, username, userpwd, email, isadmin) VALUES ('admin', '관리자', SHA2('1234', 256), 'admin@ssafy.com', 1);

CREATE TABLE BOARD (
	articleno 	int 			primary key 	auto_increment,
    writer 		varchar(20) 	not null,
    title	 	varchar(200) 	not null,
    content 	varchar(4000) 	not null,
    likey 		int 			default 0,
    regtime 	timestamp 		default current_timestamp,
    isdeleted 	int 			default 0,
    constraint fk_board_writer foreign key (writer) references USER(userid) on delete cascade
);

INSERT INTO BOARD(writer, title, content) VALUES ('ssafy', '[추천 질문] 송정동 오피스텔 추천해주세요', '이번에 자취를 하게 되었는데 부산 송정동 쪽으로 하게 될 것 같습니다. 혹시 괜찮은 오피스텔 매물이 있을까요?');
INSERT INTO BOARD(writer, title, content) VALUES ('son', '[한달 후기] 명지 ㅇㅇ아파트', '4월 말에 명지로 이사가서 한 달째 잘 살고 있습니다. 근처에 편의시설 모두 갖추어져 있고 주변 바다도 한눈에 보여서 좋아요.');
INSERT INTO BOARD(writer, title, content) VALUES ('choi', '[꿀팁 공유] 전세 사기 당하지 않는 방법', '20대 분들은 가능한 부모님과 같은 가까운 어른들을 모시고 부동산에 가도록 하세요. 어려보이면 은근 안 좋은 매물을 비싸게 보여주더라고요.');
INSERT INTO BOARD(writer, title, content) VALUES ('ssafy', '[질문] 전세인데 어디까지 가능한가요?', '벽에 못질하면 잡혀가나요?');
INSERT INTO BOARD(writer, title, content) VALUES ('son', '[꿀팁 공유] 층간소음 대처', '꼭대기층에 살면 됩니다.');
INSERT INTO BOARD(writer, title, content) VALUES ('choi', '[추천 질문] 학군 좋은 지역', '부산에 학군 괜찮은 지역 어디있나요? 아이가 곧 학교에 들어갈 나이가 되는데 신경이 쓰이네요.');
INSERT INTO BOARD(writer, title, content) VALUES ('ssafy', '[한달 후기] 고시원 생활기', '절박하신 분 아니면 살기 힘듭니다. 좋은 말 할 때 원룸이라도 가세요.');

CREATE TABLE REPLY (
	replyid 	int 			primary key 	auto_increment,
    articleno	int				not null,
	writer 		varchar(20)		not null,
    content 	varchar(1000) 	not null,
    regtime 	timestamp 		default current_timestamp,
    isdeleted 	int 			default 0,
    constraint fk_reply_writer foreign key (writer) references USER(userid) on delete cascade
);

INSERT INTO REPLY(articleno, writer, content) VALUES ('1', 'son', '명지 추천합니다');
INSERT INTO REPLY(articleno, writer, content) VALUES ('4', 'ssafy','네.');
INSERT INTO REPLY(articleno, writer, content) VALUES ('5', 'kim','좋네요~');
INSERT INTO REPLY(articleno, writer, content) VALUES ('5', 'choi','이거 완전 꿀팁이네요');
INSERT INTO REPLY(articleno, writer, content) VALUES ('5', 'ssafy','명쾌하다');
INSERT INTO REPLY(articleno, writer, content) VALUES ('7', 'kim','ㅠㅠ');
INSERT INTO REPLY(articleno, writer, content) VALUES ('7', 'choi','진심이 느껴지네요..');
INSERT INTO REPLY(articleno, writer, content) VALUES ('7', 'son','한줄요약 감사합니다');

CREATE TABLE LIKEY (
	userid 		varchar(20),
    articleno	int,
    primary key (userid, articleno),
    constraint fk_likey_userid foreign key (userid) references USER(userid) on delete cascade,
    constraint fk_likey_articleno foreign key (articleno) references BOARD(articleno) on delete cascade
);

INSERT INTO LIKEY(userid, articleno) VALUES ('son', '4');
INSERT INTO LIKEY(userid, articleno) VALUES ('kim', '4');
INSERT INTO LIKEY(userid, articleno) VALUES ('ssafy', '5');
INSERT INTO LIKEY(userid, articleno) VALUES ('choi', '5'); 
INSERT INTO LIKEY(userid, articleno) VALUES ('kim', '5');
INSERT INTO LIKEY(userid, articleno) VALUES ('son', '7');
INSERT INTO LIKEY(userid, articleno) VALUES ('kim', '7');

CREATE TABLE MESSAGE (
	messageid	int				primary key 	auto_increment,
    send 		varchar(20) 	not null,
    receive 	varchar(20) 	not null,
    title 		varchar(50) 	not null,
    content 	varchar(500) 	not null,
    regtime 	timestamp 		default current_timestamp,
    isdeleted	int				default 0,					# 0(default) 1(보낸 사람 삭제) 2(받은 사람 삭제) 3(둘 다 삭제)
    constraint fk_message_send foreign key (send) references USER(userid) on delete cascade,
    constraint fk_message_receive foreign key (receive) references USER(userid) on delete cascade
);

INSERT INTO MESSAGE(send, receive, title, content) VALUES ('choi', 'son','안녕하세요~', '매물보고 연락드립니다~');
INSERT INTO MESSAGE(send, receive, title, content) VALUES ('son', 'choi','안녕하세요~', '궁금한거 있으신가요');

CREATE TABLE SEARCHAPT (
	dongcode varchar(10) primary key,
    cnt int default 0,
    constraint fk_searchapt_dongcode foreign key (dongcode) references dongcode(dongCode)
);

CREATE TABLE BOOKMARK (
	userid varchar(20),
    aptCode bigint,
    primary key (userid, aptCode),
    constraint fk_bookmark_userid foreign key (userid) references USER(userid) on delete cascade,
    constraint fk_bookmark_aptCode foreign key (aptCode) references houseinfo(aptCode)
);

INSERT INTO SEARCHAPT (dongcode) SELECT dongCode FROM dongcode;