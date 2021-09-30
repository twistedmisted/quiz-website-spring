delete from question;
delete from quiz;
alter table quiz AUTO_INCREMENT=1;

insert into quiz (name, time, difficulty, subject) values
('first quiz', 10, 'easy', 'Java'),
('second quiz', 10, 'easy', 'HTML');

insert into question (prompt, quiz_id) values
('Question 1', 1),
('Question 2', 1),
('Question 3', 2),
('Question 4', 2);


