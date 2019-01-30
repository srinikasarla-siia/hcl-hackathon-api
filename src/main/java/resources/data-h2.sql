insert into amenities (id, type) values (1, 'Apple TV');
insert into amenities (id, type) values (2, 'Mac');
insert into amenities (id, type) values (3, 'Phone');
insert into amenities (id, type) values (4, 'Television');
insert into amenities (id, type) values (5, 'Whiteboard/marker');
insert into amenities (id, type) values (6, 'Zoom room');

insert into rooms (id, name, building_name, capacity, floor, city) values (1, 'Death Star', 'Sweet Candy Building', 4, 'Second', 'Irving');
insert into rooms (id, name, building_name, capacity, floor, city) values (2, 'Glass Case of Emotion', 'Sweet Candy Building', 10, 'Third', 'Irving');
insert into rooms (id, name, building_name, capacity, floor, city) values (3, 'Joe Pool Lake', 'Cypress', 8, 'Third', 'Dallas');


insert into room_amenities (room_id, amenities_id) values (1,1);
insert into room_amenities (room_id, amenities_id) values (1,3);
insert into room_amenities (room_id, amenities_id) values (1,4);
insert into room_amenities (room_id, amenities_id) values (2,2);
insert into room_amenities (room_id, amenities_id) values (2,4);
insert into room_amenities (room_id, amenities_id) values (3,6);
insert into room_amenities (room_id, amenities_id) values (3,1);
insert into room_amenities (room_id, amenities_id) values (3,3);