# Задание 

Чтение и запись: InputStreamReader, FileWriter

Формат файлов: csv

Коллекция: TreeMap

Доменные объекты: 

```
public class Ticket {
    private Integer id; 
    private String name; 
    private Coordinates coordinates; 
    private java.util.Date creationDate; 
    private long price; 
    private float discount; 
    private boolean refundable;
    private TicketType type; 
    private Venue venue; 
}
public class Coordinates {
    private int x; 
    private Long y; 
}
public class Venue {
    private Long id; 
    private String name; 
    private Integer capacity;
}
public enum TicketType {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;
}
```
Команды:
* average_of_discount
* clear
* insert {key} {ticket}
* print_ascending 
* print_descending
* remove_by_key {key}
* remove_greater {ticket}
* replace_if_greater {key} {ticket}
* save
* show
* update {id}

# Исполнение

Пример исполнения в интерактивном режиме (режим при котором пользователь вводит поля объекта):


```
> insert 255
Enter ticket name. Can't be null or empty

Ticket name must be string, can't be null or empty
Enter ticket name. Can't be null or empty
HI
Enter x-coordinate for ticket
1.0
Enter y-coordinate for ticket. Must be greater than -615, can't be null
-600
Enter ticket price. Must be greater than 0
-100
Ticket price must be an integer, greater than 0
Enter ticket price. Must be greater than 0
100
Enter ticket discount. Allowed range is from 0 to 100
10
Is ticket refundable? If so enter true
true
Enter ticket type. Supported types: vip, usual, cheap
usual
Enter ticket's venue name, can't be null or empty
Venue
Enter ticket's venue capacity, must be greater than 0
100
Executed successfully! Inserted ticket:
Ticket {
  id=1056358265385433223,
  name='HI',
  coordinates=Coordinates{x=1.0, y=-600.0},
  creationDate=2025-04-28T16:46:35.472186200+03:00[Europe/Moscow],
  price=100,
  discount=10,
  refundable=true,
  type=USUAL,
  venue=Venue {
  id=6628374360632151386,
  name='Venue',
  capacity=100
}
}
>
```
Пример исполнения на тестовых скриптах (/data/scripts): </p>
**test_recursion**
```
> execute_script test_recursion
Executed successfully! Inserted ticket:
Ticket {
  id=2306086355010695225,
  name='basic_ticket1',
  coordinates=Coordinates{x=500.0, y=900.0},
  creationDate=2025-04-28T16:48:34.084164800+03:00[Europe/Moscow],
  price=1000,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=1101141014170585041,
  name='basic_venue1',
  capacity=200
}
}
Executed successfully! Inserted ticket:
Ticket {
  id=7402269950815156044,
  name='basic_ticket2',
  coordinates=Coordinates{x=120.0, y=10.0},
  creationDate=2025-04-28T16:48:34.086165400+03:00[Europe/Moscow],
  price=7000,
  discount=10,
  refundable=true,
  type=VIP,
  venue=Venue {
  id=5059141388439302427,
  name='basic_venue2',
  capacity=10
}
}
Executed successfully! Inserted ticket:
Ticket {
  id=3627084951487531578,
  name='basic_ticket3',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.088164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=2976662330120909734,
  name='basic_venue3',
  capacity=10
}
}
Executed successfully! Inserted ticket:
Ticket {
  id=1552883097261291471,
  name='basic_ticket4',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.089164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=5213122361640239528,
  name='basic_venue3',
  capacity=401
}
}
Script executed!
Script executed!
Executed successfully! Current tickets:
[Ticket {
  id=3293613585587156667,
  name='basic_ticket',
  coordinates=Coordinates{x=0.0, y=3.0},
  creationDate=2025-04-25T14:08:30.373668+03:00[Europe/Moscow],
  price=10,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=956189794113598383,
  name='basic_venue',
  capacity=10
}
}, Ticket {
  id=2306086355010695225,
  name='basic_ticket1',
  coordinates=Coordinates{x=500.0, y=900.0},
  creationDate=2025-04-28T16:48:34.084164800+03:00[Europe/Moscow],
  price=1000,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=1101141014170585041,
  name='basic_venue1',
  capacity=200
}
}, Ticket {
  id=7402269950815156044,
  name='basic_ticket2',
  coordinates=Coordinates{x=120.0, y=10.0},
  creationDate=2025-04-28T16:48:34.086165400+03:00[Europe/Moscow],
  price=7000,
  discount=10,
  refundable=true,
  type=VIP,
  venue=Venue {
  id=5059141388439302427,
  name='basic_venue2',
  capacity=10
}
}, Ticket {
  id=3627084951487531578,
  name='basic_ticket3',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.088164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=2976662330120909734,
  name='basic_venue3',
  capacity=10
}
}, Ticket {
  id=1552883097261291471,
  name='basic_ticket4',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.089164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=5213122361640239528,
  name='basic_venue3',
  capacity=401
}
}, Ticket {
  id=1056358265385433223,
  name='HI',
  coordinates=Coordinates{x=1.0, y=-600.0},
  creationDate=2025-04-28T16:46:35.472186200+03:00[Europe/Moscow],
  price=100,
  discount=10,
  refundable=true,
  type=USUAL,
  venue=Venue {
  id=6628374360632151386,
  name='Venue',
  capacity=100
}
}]
Recursive script execution detected. Skipping this script
Script executed!
Script executed!
```

**test_insert_and_update**
```
> execute_script test_insert_and_update
Executed successfully! Inserted ticket:
Ticket {
  id=4613081819179103323,
  name='basic_ticket',
  coordinates=Coordinates{x=0.0, y=3.0},
  creationDate=2025-04-28T16:50:11.370849500+03:00[Europe/Moscow],
  price=10,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=4682072419323263586,
  name='basic_venue',
  capacity=10
}
}
Ticket successfully updated. New ticket value:
Ticket {
  id=3293613585587156667,
  name='basic_ticket',
  coordinates=Coordinates{x=0.0, y=3.0},
  creationDate=2025-04-25T14:08:30.373668+03:00[Europe/Moscow],
  price=1,
  discount=100,
  refundable=false,
  type=CHEAP,
  venue=Venue {
  id=956189794113598383,
  name='basic_venue',
  capacity=100
}
}
Executed successfully! Current tickets:
[Ticket {
  id=3293613585587156667,
  name='basic_ticket',
  coordinates=Coordinates{x=0.0, y=3.0},
  creationDate=2025-04-25T14:08:30.373668+03:00[Europe/Moscow],
  price=1,
  discount=100,
  refundable=false,
  type=CHEAP,
  venue=Venue {
  id=956189794113598383,
  name='basic_venue',
  capacity=100
}
}, Ticket {
  id=4613081819179103323,
  name='basic_ticket',
  coordinates=Coordinates{x=0.0, y=3.0},
  creationDate=2025-04-28T16:50:11.370849500+03:00[Europe/Moscow],
  price=10,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=4682072419323263586,
  name='basic_venue',
  capacity=10
}
}, Ticket {
  id=2306086355010695225,
  name='basic_ticket1',
  coordinates=Coordinates{x=500.0, y=900.0},
  creationDate=2025-04-28T16:48:34.084164800+03:00[Europe/Moscow],
  price=1000,
  discount=30,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=1101141014170585041,
  name='basic_venue1',
  capacity=200
}
}, Ticket {
  id=7402269950815156044,
  name='basic_ticket2',
  coordinates=Coordinates{x=120.0, y=10.0},
  creationDate=2025-04-28T16:48:34.086165400+03:00[Europe/Moscow],
  price=7000,
  discount=10,
  refundable=true,
  type=VIP,
  venue=Venue {
  id=5059141388439302427,
  name='basic_venue2',
  capacity=10
}
}, Ticket {
  id=3627084951487531578,
  name='basic_ticket3',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.088164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=2976662330120909734,
  name='basic_venue3',
  capacity=10
}
}, Ticket {
  id=1552883097261291471,
  name='basic_ticket4',
  coordinates=Coordinates{x=11.0, y=3.0},
  creationDate=2025-04-28T16:48:34.089164600+03:00[Europe/Moscow],
  price=234,
  discount=11,
  refundable=true,
  type=CHEAP,
  venue=Venue {
  id=5213122361640239528,
  name='basic_venue3',
  capacity=401
}
}, Ticket {
  id=1056358265385433223,
  name='HI',
  coordinates=Coordinates{x=1.0, y=-600.0},
  creationDate=2025-04-28T16:46:35.472186200+03:00[Europe/Moscow],
  price=100,
  discount=10,
  refundable=true,
  type=USUAL,
  venue=Venue {
  id=6628374360632151386,
  name='Venue',
  capacity=100
}
}]
Script executed!
```