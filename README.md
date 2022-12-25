<h1 align="center">Вдохновление бралось <a href="https://www.youtube.com/watch?v=OjNpRbNdR7E" target="_blank">отсюда</a> 
<img src="https://cdn3.emoji.gg/emojis/4583-peepochina.gif" height="32"/></h1>
<h3 align="center">Партия гордится вами и выдать одна кошка-жена  <img src="https://cdn3.emoji.gg/emojis/5803-chinardia.png" height="32"/></h3>
<a href="https://git.io/typing-svg"><img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=32&duration=3000&pause=1000&color=3F6CF7&center=true&vCenter=true&width=1000&height=200&lines=%D0%A1%D0%BF%D1%83%D1%81%D1%82%D1%8F+%D0%BC%D0%B5%D1%81%D1%8F%D1%86%D1%8B+%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B8...;%D0%9D%D0%B5%D0%B4%D0%B5%D0%BB%D0%B8+%D0%BF%D0%B0%D1%80%D1%81%D0%B5%D1%80%D1%81%D1%82%D0%B2%D0%B0...;%D0%94%D0%BD%D0%B8+%D1%80%D0%B5%D0%B4%D0%B0%D0%BA%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F+%D1%84%D0%B0%D0%B9%D0%BB%D0%B0+README.md...;%D0%AF+%D0%BF%D1%80%D0%B5%D0%B4%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D1%8F%D1%8E+%D0%AD%D0%A2%D0%9E!;%D0%A1%D0%BF%D1%83%D1%81%D1%82%D1%8F+%D0%BC%D0%B5%D1%81%D1%8F%D1%86%D1%8B+%D1%80%D0%B0%D0%B7%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B8...;%D0%9D%D0%B5%D0%B4%D0%B5%D0%BB%D0%B8+%D0%BF%D0%B0%D1%80%D1%81%D0%B5%D1%80%D1%81%D1%82%D0%B2%D0%B0...;%D0%94%D0%BD%D0%B8+%D1%80%D0%B5%D0%B4%D0%B0%D0%BA%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D1%8F+%D1%84%D0%B0%D0%B9%D0%BB%D0%B0+README.md...;%D0%AF+%D0%BF%D1%80%D0%B5%D0%B4%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D1%8F%D1%8E+%D0%AD%D0%A2%D0%9E!;%D1%85%D1%8D%D0%B9%2C+%D0%BD%D0%B0+%D1%81%D0%B2%D1%8F%D0%B7%D0%B8+%D0%BB%D0%B8%D1%87%D0%BD%D1%8B%D0%B9+%D0%BF%D0%B0%D1%80%D1%81%D0%B5%D1%80+%D0%90%D1%80%D1%82%D0%B5%D0%BC%D0%B0...;%D0%BE%D0%BD+%D0%B7%D0%B0%D1%81%D1%82%D0%B0%D0%B2%D0%BB%D1%8F%D0%BB+%D0%BC%D0%B5%D0%BD%D1%8F+%D0%BF%D0%B0%D1%80%D1%81%D0%B8%D1%82%D1%8C...;...%D1%81%D0%BE%D1%82%D0%BD%D0%B8+%D1%82%D0%BE%D0%B2%D0%B0%D1%80%D0%BE%D0%B2+%D0%B4%D0%B5%D0%BD%D1%8C+%D0%B8+%D0%BD%D0%BE%D1%87%D1%8C+%D0%B7%D0%B0+2+%D1%87%D0%B0%D1%88%D0%BA%D0%B8+%D0%BA%D0%BE%D1%84%D0%B5...+;%D0%B8+%D1%80%D0%B0%D0%B4%D0%B8+%D1%87%D0%B5%D0%B3%D0%BE%2C+%D1%80%D0%B0%D0%B4%D0%B8+%D0%BA%D0%B0%D0%BA%D0%BE%D0%B3%D0%BE-%D1%82%D0%BE+%D0%B1%D0%BE%D1%82%D0%B0%3F..." alt="Typing SVG" /></a>


# Карта разработки бота BuildPCBot для сборок ПК
**_Участвующие в разработке: Артём Путинцев, Леон Озеров, Евгений Потеряев, Виталик Парсер_**





### Этап 1. Основа с минимальной функциональностью бота

- Бот берет из json-файла заранее добавленные данные о комплектующих и собирает из них сборку по необходимому бюджету.

- Пример работы с ботом:
    - -> `/buildPC`  
      ---- "Введите бюджет"  
      -> __60000__  
      ---- "Производитель процессора Intel или AMD?"  
      -> __intel / amd__  
      ---- Производитель видеокарты NVIDIA или AMD?  
      -> __nvidia / amd__  
      ---- "Вот оптимальная сборка: ..."

    - В случае невозможности собрать ПК при такой цене выводится ошибка.





### Этап 2. Расширение функционала бота. Первые реальные тесты
- Добавление функции проверки совместимости комплектующих
- Добавление взаимодействия с ботом через кнопки
- Пример работы с ботом:

    - -> `/buildPC`  
      ---- "Введите бюджет"  
      -> 60000  
      ---- "Производитель процессора Intel или AMD?" + кнопки `[Intel | AMD]`  
      -> __[Intel]__  
      ---- Производитель видеокарты NVIDIA или AMD? + кнопки `[NVIDIA | AMD]`
      -> __nvidia / amd__  
      ---- "Вот оптимальная сборка: ..."






### Этап 3. Завершение основной части разработки бота. Планирование расширения

- Добавление возможности редактировать сохраненные сборки

- Пример работы с ботом:

    - -> `/buildPC`  
      ---- "Введите бюджет"  
      -> 60000  
      ---- "Производитель процессора Intel или AMD?" + кнопки `[Intel | AMD]`  
      -> __[Intel]__  
      ---- Производитель видеокарты NVIDIA или AMD? + кнопки `[NVIDIA | AMD]`  
      -> кнопка __[NVIDIA]__  
      ---- "Вот оптимальная сборка... + кнопка на сообщении `[Сохранить]`  
      -> кнопка __[Сохранить]__  
      ---- "Введите имя для сборки"  
      -> ...

### Этап 4. Рейтинг сборок.

- Возможность сохранить сборку публично
- Рейтинг всех публично сохраненных сборок
- Пример работы с ботом:

    - -> `/buildPC`  
      ---- "Введите бюджет"  
      -> 60000  
      ---- "Производитель процессора Intel или AMD?" + кнопки `[Intel | AMD]`  
      -> __[Intel]__  
      ---- Производитель видеокарты NVIDIA или AMD? + кнопки `[NVIDIA | AMD]`  
      -> кнопка __[AMD]__  
      ---- "Вот оптимальная сборка... + кнопка на сообщении `[Сохранить]`    
      -> кнопка __[Сохранить]__   
      ---- "Выберит вариант сохранения" + кнопки на сообщении `[Публично | Для себя]`  
      -> ...  
      `После сохранения`  
      -> `/builds`  
      ---- "Лучшие сборки от пользователей" + кнопки с названием сборок, например `Best pc ever`  
      -> кнопка __[Best pc ever]__  
      ---- "Сборка от __[Пользователь]__ Лайков: 77 сборка ..." + Кнопки `[Лайк | Назад]`

### Этап 5.
 - Возможность добавить комментарий к своей сборке
 - Возможность найти отдельно комплектующие в нужном ценовом сегменте
 - Юнит тесты
 - Пример работы с ботом:

   - `После сохранения`    
     ---- "Оставить комментарий?" + кнопки `[Да | Нет]`  
     -> кнопка __[Да]__   
     ---- "Введите комментарий"   
     -> ...
   - `В просмотре сборки`   
    ---- Новая кнопка `["Добавить комментарий" в случае отсутсвия, иначе "редактировать комментарий"]`
   - `/search`    
    ---- "Выберите что искать" + Кнопки `[cpu | gpu | motherboard | ...]`   
    -> кнопка __[cpu]__
    ---- "Введите желаемую стоимость"   
    -> 25000   
    ---- "Вот процессоры в нужном ценовом сегменте: " + Список найденных комплектующих   
   


### Этап 6.
 - Залить проект на хостинг
 - Возможность выбора комплектующих на каждом этапе (Комлпектующие соответствуют ценовому сегменту)
 - Пример работы с ботом:

   - -> `/buildPC`  
   --- "Расширенный режим сборки?" + кнопки `[Да | Нет]`   
   -> кнопка __[Да]__   
   ---- "Введите бюджет"  
   -> 60000  
   ---- "Производитель процессора Intel или AMD?" + кнопки `[Intel | AMD]`  
   -> __[Intel]__  
   ---- "Вот доступные варианты" + кнопки с процессорами  
   -> кнопка с процессором   
   ---- Производитель видеокарты NVIDIA или AMD? + кнопки `[NVIDIA | AMD]`  
   -> кнопка __[AMD]__  
   ---- "Вот доступные варианты" + кнопки с видеокартами  
   -> кнопка с видеокартой
   и т.д. для всех частей
   



### Доступные команды:
`/start` - Приветствие и обзор возможностей бота с небольшой инструкцией и примером использования

`/help` - Вывод помощи по работе с ботом и вывод доступных команд

`/buildPC` - Начало работы с ботом (сборка ПК)

`/cancel` - Отменить текущее действие

`/load` - Посмотреть сохраненные сборки

`/saved` - Посмотреть сохраненные сборки пк

`/delete` - Удалить всю информацию об текущем пользователе

`/builds` - Посмотреть сборки от других пользователей

`/search` - Поиск комплектующих из доступных в указанном ценовом сегменте




