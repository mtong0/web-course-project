create project;
drop table user_vote_question if exists;
drop table user_vote_answer if exists;
drop table user_mark_answer if exists;
drop table user_like_answer if exists;
drop table reply if exists;
drop table answer if exists;
drop table question if exists;
drop table user if exists;
drop table hibernate_sequence if exists;

create table hibernate_sequence
(
	next_val bigint null
);

create table user
(
	id int not null
		primary key,
	email varchar(255) null,
	experience int not null,
	nick_name varchar(255) null,
	password varchar(255) null,
	topics varchar(255) null,
	vector varchar(255) null
);

create table question
(
	id int not null
		primary key,
	create_date datetime(6) null,
	description varchar(255) null,
	modified_date datetime(6) null,
	name varchar(255) null,
	poster_id int null,
	rank_point int not null,
	vote int not null,
	keywords varchar(255) null,
	constraint FKb6c54l62jbc7u9r87toj37jq5
		foreign key (poster_id) references user (id)
);

create table answer
(
	id int not null
		primary key,
	content varchar(4000) null,
	create_date datetime(6) null,
	`like` int null,
	mark int not null,
	poster_id int null,
	question_id int null,
	rank_point int not null,
	top_ten_words varchar(255) null,
	vote int not null,
	topics varchar(255) null,
	constraint FK2dcawe4g75q6ux3bywcaokowp
		foreign key (poster_id) references user (id),
	constraint FK8frr4bcabmmeyyu60qt7iiblo
		foreign key (question_id) references question (id)
);

create table reply
(
	id int not null
		primary key,
	answer_id int null,
	content varchar(255) null,
	create_date datetime(6) null,
	`like` int null,
	poster_id int null,
	constraint FKaf5yg71spcu128tsowm2m5dse
		foreign key (poster_id) references user (id),
	constraint FKlkrkt9is6eyijhneochdc0lhy
		foreign key (answer_id) references answer (id)
);

create table user_follow_question
(
	user_id int not null,
	question_id int not null,
	constraint FKcl26fxmglvaw009x6p4cqi9mo
		foreign key (question_id) references question (id),
	constraint FKg5ui9rasxjq3yftatvyfmo4cp
		foreign key (user_id) references user (id)
);

create table user_like_answer
(
	user_id int not null,
	answer_id int not null,
	id int auto_increment
		primary key,
	constraint FK33wwppc2krj11im0cvsrxbv1f
		foreign key (user_id) references user (id),
	constraint FKnr84nkhufyi7qmnbty3naxn2d
		foreign key (answer_id) references answer (id)
);

create table user_mark_answer
(
	id int not null
		primary key,
	answer_id int null,
	tags varchar(255) null,
	user_id int null,
	constraint FKby0t74j6bxsd1fh720ps5pn5y
		foreign key (user_id) references user (id),
	constraint FKd3ni0csf9trrbwcw1uu0abygq
		foreign key (answer_id) references answer (id)
);

create table user_vote_answer
(
	user_id int not null,
	answer_id int not null,
	id int auto_increment
		primary key,
	constraint FK29jnd2s84jbmrymvqkpnmy3e6
		foreign key (answer_id) references answer (id),
	constraint FKk7p65t1ayoj8ods8jq3tla87b
		foreign key (user_id) references user (id)
);

create table user_vote_question
(
	user_id int not null,
	question_id int not null,
	id int auto_increment
		primary key,
	constraint FK4cgfi8r9tppolhyicj0192jdx
		foreign key (user_id) references user (id),
	constraint FK9apycs0598h6cmbnm5046d50
		foreign key (question_id) references question (id)
);

create definer = root@localhost function substrCount(s varchar(4000), ss varchar(4000)) returns int unsigned reads sql data
BEGIN
    DECLARE count INT(4) UNSIGNED;
    DECLARE offset INT(4) UNSIGNED;
    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET s = NULL;

    SET count = 0;
    SET offset = 1;

    REPEAT
        IF NOT ISNULL(s) AND offset > 0 THEN
            SET offset = REGEXP_INSTR(s, ss, offset);
            IF offset > 0 THEN
                SET count = count + 1;
                SET offset = offset + 1;
            END IF;
        END IF;
    UNTIL ISNULL(s) OR offset = 0 END REPEAT;

    RETURN count;
END;

INSERT INTO project.hibernate_sequence (next_val) VALUES (46);

INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (0, '', 'fakeuser@email.com', '123456', 0, null, '{}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (9, 'test user 1', 'testuser1@email.com', '123456', 350, '', '{}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (10, 'test user 2', 'testuser2@email.com', '123456', 100, '', '{"vaccine":2,"rhino":3,"government":2,"gene":2,"pool":2,"variant":2,"animals":3,"look":2,"population":4,"attempts":2}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (11, 'test user 3', 'testuser3@email.com', '123456', 120, '', '{"tech":2,"new":2,"emiconductor":4,"hockley":3,"batch":3,"you":5,"transistor":2,"airchild":2,"drink":2,"beer":2}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (12, 'test user 5', 'testuser5@email.com', '123456', 265, '', '{"waves":3,"light":4,"occur":2,"ones":3,"ake":2,"turn":4,"around":4,"life":3,"way":2,"wrong":4}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (13, 'test user 6', 'testuser6@email.com', '123456', 500, '', '{"new":2,"batch":3,"ask":1,"hat":2,"hops":1,"after":1,"ale":1,"recent":1,"beer":4,"drink":4}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (14, 'test user 7', 'testuser7@email.com', '123456', 40, '', '{"emiconductor":4,"find":3,"hockley":3,"ake":2,"selfhelp":3,"tips":3,"transistor":2,"airchild":2,"wrong":4,"goals":3}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (15, 'test user 8', 'testuser8@email.com', '123456', 155, '', '{"tech":2,"became":2,"emiconductor":4,"hockley":3,"rize":2,"obel":2,"company":2,"ilicon":2,"transistor":2,"airchild":2}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (16, 'test user 4', 'testuser4@email.com', '123456', 205, '', '{}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (17, 'test user 9', 'testuser9@email.com', '123456', 0, 'history,music,', '{}');
INSERT INTO project.user (id, nick_name, email, password, experience, topics, vector) VALUES (19, 'test user 10', 'testuser10@email.com', '123456', 50, 'history,music,', '{}');

INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (19, 'What are some of the most bizarre scientific experiments?', null, '2021-11-30', '2021-11-30', 15, 2, 20, 'bizarre,scientific,experiments');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (21, 'Is it possible that omicron may be the covid variant where the government attempts a vaccine mandate for the entire population?', null, '2021-11-30', '2021-11-30', 15, 1, 10, 'covid,vaccine,mandate,omicron,government,variant,entire,attempts,population');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (25, 'Who was a very unpleasant Nobel Prize winner?', null, '2021-11-30', '2021-11-30', 9, 1, 10, 'winner,unpleasant,nobel,prize');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (28, 'What should you do if you drink too much beer?', null, '2021-12-03', '2021-12-03', 12, 1, 10, 'drink,beer');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (29, 'Why does damping not occur in light waves?', null, '2021-12-03', '2021-12-03', 12, 0, 0, 'waves,light,occur,why,damping');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (32, 'Is it practically possible to turn one''s life around in 6 months?', null, '2021-12-03', '2021-12-03', 15, 1, 10, 'months,practically,ones,turn,around,life');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (38, 'What are some self-help tips to find motivation and reach goals?', null, '2021-12-03', '2021-12-03', 16, 1, 10, 'find,reach,motivation,selfhelp,tips,goals');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (40, 'What should I do when I am nervous and worried? How can I stop it?', null, '2021-12-03', '2021-12-03', 12, 0, 0, 'stop,nervous,worried');
INSERT INTO project.question (id, name, description, create_date, modified_date, poster_id, vote, rank_point, keywords) VALUES (42, 'When will the Pandemic End? And How?', null, '2021-12-03', '2021-12-03', 19, 0, 0, 'end,pandemic');
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (20, 19, 9, '2021-11-30', 1, 'Take a look at this picture, a nice long look.

Now, tell me what is going on here.

No, you are not hallucinating. You are not on drugs, or high. This is not photoshopped.

This is actually a rhino, hanging upside down from its ankles, being dangled by a helicopter.

Your first thought might be that this is horrendous! The poor animals are going to be severely hurt! Surely, this must be the work of someone without a soul.

Nope, this is a science experiment, being funded by Namibian Ministry of Environment. And it’s actually helping them to save the rhino population from dwindling even more.

You see, due to widespread poaching of these animals, their populations have become very low, with most of the animals being concentrated in places like national parks and wildlife reserves. However, there’s a problem which arises due to this high concentration - inbreeding.

Rhinos, unable to find mates from outside, have to resort to mating among their own gene pool. This, of course, leads to a lot of problems - none of which are good for the rhino population.

Because of this, groups dedicated to preserving these majestic beasts have been shifting rhinos away from their families when they reach maturity, so that they can reproduce outside their own gene pool, and prevent inbreeding.', 1, 0, 15, '{"animals": 3,"rhino": 3,"inbreeding": 2,"population": 2,"look": 2,"pool": 2,"gene": 2,"high": 2,"own": 2,"first": 1,"hanging": 1,"ake": 1,"arises": 1,"upside": 1,"places": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (22, 21, 9, '2021-11-30', 0, 'It sure smells like it…. Believe me they are desperate to shove their worthless dangerous vaccine in us at any cost. You would think they were up to something nefarious. They act like it.', 0, 0, 0, '{"vaccine": 2,"attempts": 1,"variant": 1,"government": 1,"omicron": 1,"act": 1,"nefarious": 1,"worthless": 1,"population": 1,"something": 1,"elieve": 1,"entire": 1,"shove": 1,"dangerous": 1,"desperate": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (24, 21, 10, '2021-11-30', 1, 'Possible yes, likely no, nothing is known about the variant, so drawing conclusions based on not knowing what the variant actually does and if it is more dangerous than Delta is quite pointless.

Anybody who tells you they know what will happen with the new variant is just lying.', 0, 0, 10, '{"variant": 4,"elta": 1,"ossible": 1,"knowing": 1,"vaccine": 1,"tells": 1,"population": 1,"conclusions": 1,"yes": 1,"pointless": 1,"new": 1,"happen": 1,"nybody": 1,"based": 1,"likely": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (27, 25, 16, '2021-12-02', 3, 'My vote goes to this man: William Shockley.
He shared the Nobel Prize in Physics in 1956 for developing the transistor. His co-researchers John Bardeen and Walter Brattain waited for him to be out of the lab before they announced the first working transistor, they hated him so much.

He is the reason Silicon Valley became America’s tech hub. He set up a company in Palo Alto called Shockley Semiconductor, where he became infamous as a paranoid, controlling, vindictive, angry manager who frequently abused his employees. He was so loathed and despised by the people who worked for him that eight of his top researchers rebelled and left the company to form Fairchild Semiconductor. Fairchild Semiconductor went on to be the incubator for many of Silicon Valley’s most important tech firms.

By that point he had such a reputation as a domineering, vicious autocrat that nobody else would work for him, and Shockley Semiconductor failed.

He was a racist and a proponent of eugenics. He turned his attention away from solid-state physics and spent the last fifteen years of his life attempting to find “scientific proof” that black people were inferior to whites.

', 1, 3, 38, '{"emiconductor": 4,"hockley": 3,"airchild": 2,"transistor": 2,"company": 2,"ilicon": 2,"became": 2,"tech": 2,"rize": 2,"obel": 2,"abused": 1,"coresearchers": 1,"mericas": 1,"first": 1,"racist": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (30, 28, 13, '2021-12-03', 2, '“What should you do if you drink too much beer?”

Start a new batch. You DID ask this question in a home brewing space after all.

So, what have you started as your new batch? My most recent batch of ale was an English bitter than I added enough extra hops to seem like an IPA. Delicious.', 1, 1, 26, '{"batch": 3,"drink": 2,"new": 2,"beer": 2,"hat": 2,"hops": 1,"ask": 1,"home": 1,"enough": 1,"started": 1,"question": 1,"all": 1,"recent": 1,"ale": 1,"after": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (31, 29, 13, '2021-12-03', 1, 'Damping does occur in light waves, whenever a portion of the light wave is absorbed. Absorption reduces the light wave’s energy, or “damps” it.', 0, 1, 11, '{"light": 4,"waves": 3,"occur": 2,"energy": 1,"damps": 1,"reduces": 1,"whenever": 1,"absorbed": 1,"wave": 1,"damping": 1,"portion": 1,"bsorption": 1,"amping": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (33, 32, 11, '2021-12-03', 1, 'Despite having GPS on my phone, I was giving my husband directions and suddenly realized I was telling him to go the wrong way because I had the wrong address plugged in. With five minutes:

    I decided we were going the wrong way.
    I figured out where it was safe for us to turn the car around.
    We turned and got back onto the freeway heading in the right direction.

So, yes, even though it’s not as quick as driving a car, it’s entirely possible.

Writer Barbara Kingsolver said, “The changes we dread most may contain our salvation.” The process is not easy but simple:

    Recognize you need to change.
    Make a different choice.
    Take new action.

Of course, this needs to be repeated day in and day out, consistently.', 1, 2, 17, '{"wrong": 3,"ake": 2,"way": 2,"car": 2,"around": 2,"turn": 2,"riter": 1,"choice": 1,"ones": 1,"ingsolver": 1,"entirely": 1,"suddenly": 1,"realized": 1,"change": 1,"yes": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (37, 38, 0, '2021-12-03', 0, 'No answers yet', 0, 0, -1, '{"goals": 1,"tips": 1,"selfhelp": 1,"hat": 1,"yet": 1,"answers": 1,"motivation": 1,"reach": 1,"find": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (39, 38, 12, '2021-12-03', 2, 'In life, they will always be people who aren''t going to support your dreams. Not that they are bad people, but they can''t visualize you achieving them. Follow your dreams to prove them wrong.', 1, 1, 26, '{"your": 2,"dreams": 2,"goals": 1,"support": 1,"selfhelp": 1,"hat": 1,"ollow": 1,"visualize": 1,"find": 1,"arent": 1,"achieving": 1,"wrong": 1,"tips": 1,"life": 1,"cant": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (40, 40, 0, '2021-12-03', -1, 'No answers yet', 0, 0, -1, '{"worried": 1,"hat": 1,"yet": 1,"answers": 1,"nervous": 1,"stop": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (41, 40, 13, '2021-12-03', 0, 'Be nervous. Trying to stop it is a disaster. It leads to a split of your psyche so that you have a nervous part and another that is trying to stop being nervous which leads to more nervousness. It is an attempt to try to run from yourself or after yourself.

Nervousness is an unpleasant experience in contrast to say confidence and most everyone is trying to maximize pleasant sensations and do away or at least avoid unpleasant ones. This is futile as sensations in themselves are but information about your state of being and your relationship with your environment.

The notion that you can stop your self from experiencing one''s sensations is just insane. While it gives a hope of a better future (one where only pleasantness exists) it''s ultimate result is that one never exists at all and this is done while existing! So we keep on running to tomorrow, or next week or anytime that is not now where we wil be better persons and before you know it, the organism has reached its expiration date (or an accident happens) and so so we die wanting for more.

How about you just observe your nervousness and understand the information being relayed by such state of mind. This can be achieved by doing the very thing suggested in the question. Try to consciously stop yourself from feeling nervous or whatever sensations you happen to feel at the moment. Try it once, try it twice, try it more and more. Is it working? If yes, what state of being have you achieved? Keep doing this untill what is to be done or not to be done is apparent to you.', 0, 0, 0, '{"your": 6,"nervous": 5,"stop": 5,"sensations": 4,"try": 3,"done": 3,"yourself": 3,"state": 3,"achieved": 2,"ones": 2,"better": 2,"doing": 2,"leads": 2,"information": 2,"exists": 2}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (42, 42, 0, '2021-12-03', -1, 'No answers yet', 0, 0, -1, '{"andemic": 1,"yet": 1,"answers": 1,"hen": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (43, 42, 13, '2021-12-03', 0, 'I wish I could tell but I cannot. I suppose it is a combination of vaccination, new available drugs for the treatment of COVID and the cooperation of people in following the guidance of sanitary authorities in their respective jurisdictions. Give or take maybe a couple of years.', 0, 0, 0, '{"jurisdictions": 1,"cooperation": 1,"vaccination": 1,"combination": 1,"andemic": 1,"ive": 1,"cannot": 1,"following": 1,"guidance": 1,"hen": 1,"wish": 1,"years": 1,"authorities": 1,"suppose": 1,"sanitary": 1}', null);
INSERT INTO project.answer (id, question_id, poster_id, create_date, vote, content, mark, `like`, rank_point, top_ten_words, topics) VALUES (44, 42, 13, '2021-12-03', 0, 'The Pandemic will not end, It may be forced into remission , but not an end until 85 % to 95 % of the population on earth has been vaccinated .at wich point it will hide and return when a new seriously stronger Mutation breaks out and the cycle begins again.

We are dealing with evolution that has gone on for billions of years, where changes occur only when accidents are missed and develop into permanent new features written into the DNA HELIX of all living things on Earth. , not a bad automobile model that can be retroactively corrected.', 0, 0, 0, '{"andemic": 2,"new": 2,"end": 2,"return": 1,"until": 1,"earth": 1,"remission": 1,"permanent": 1,"hide": 1,"corrected": 1,"gone": 1,"population": 1,"accidents": 1,"develop": 1,"evolution": 1}', null);
INSERT INTO project.reply (id, answer_id, poster_id, content, create_date, `like`) VALUES (45, 39, 14, 'This is a good answer.', '2021-12-03 12:00:29.170000', 0);

INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (3, 15, 27);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (5, 11, 27);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (6, 11, 30);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (8, 12, 31);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (9, 12, 33);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (12, 14, 27);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (13, 14, 39);
INSERT INTO project.user_like_answer (id, user_id, answer_id) VALUES (14, 14, 33);

INSERT INTO project.user_mark_answer (id, user_id, answer_id, tags) VALUES (7, 16, 20, 'rhinos');
INSERT INTO project.user_mark_answer (id, user_id, answer_id, tags) VALUES (9, 11, 27, null);
INSERT INTO project.user_mark_answer (id, user_id, answer_id, tags) VALUES (10, 11, 30, 'drink');
INSERT INTO project.user_mark_answer (id, user_id, answer_id, tags) VALUES (11, 12, 39, 'motivation');
INSERT INTO project.user_mark_answer (id, user_id, answer_id, tags) VALUES (12, 14, 33, 'life change');

INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (11, 9, 24);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (12, 10, 20);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (13, 13, 30);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (15, 15, 27);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (17, 11, 27);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (18, 11, 30);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (20, 12, 31);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (21, 12, 39);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (27, 14, 27);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (28, 14, 39);
INSERT INTO project.user_vote_answer (id, user_id, answer_id) VALUES (29, 14, 33);

INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (7, 10, 21);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (8, 10, 19);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (9, 16, 19);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (10, 13, 28);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (11, 12, 32);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (13, 14, 38);
INSERT INTO project.user_vote_question (id, user_id, question_id) VALUES (14, 14, 25);