INSERT INTO users (user_id, name, email, created_at, updated_at)
VALUES (1, '데모 사용자', 'demo@leaflog.com', NOW(), NOW());

INSERT INTO species (species_id, name, watering_interval_days, category, description, interval_text, image_url, created_at, updated_at)
VALUES
    (1, '몬스테라', 7, '관엽식물', '밝은 간접광에서 잘 자라요', '5~7일', '/assets/monstera.png', NOW(), NOW()),
    (2, '스킨답서스', 8, '관엽식물', '초보자도 쉽게 키울 수 있어요', '7~10일', '/assets/scindapsus.png', NOW(), NOW()),
    (3, '스투키', 18, '다육·선인장', '물을 자주 주지 않아도 돼요', '2~3주', '/assets/stuckyi.png', NOW(), NOW()),
    (4, '라벤더', 7, '허브', '향기로운 허브예요', '5~7일', '/assets/lavender.png', NOW(), NOW()),
    (99, '기타', 7, NULL, NULL, NULL, NULL, NOW(), NOW());
