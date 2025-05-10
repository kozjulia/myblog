insert into posts(id, title, image_path, text, likes_count)
values (1, 'Хороший пост', '', 'Описание хорошего поста' ||
                               'Новый абзац' ||
                               'Новый абзац', 2);
insert into posts(id, title, image_path, text, likes_count)
values (2, 'Плохой пост', '', 'Описание плохого поста' ||
                              'Новый абзац', 3);
insert into posts(id, title, image_path, text, likes_count)
values (3, 'Новый пост', '', 'Описание нового поста', 1);

insert into comments(id, post_id, description)
values (1, 1, 'Коммент для хорошего поста');
insert into comments(id, post_id, description)
values (2, 2, 'Коммент для плохого поста');
insert into comments(id, post_id, description)
values (3, 3, 'Коммент для нового поста');