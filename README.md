<details>
<summary>
Глоссарий
</summary>

---

#### Атомарные операции (atomic operations)

Операции A и B являются атомарными, если, с точки зрения потока,
выполняющего операцию A, операция B либо была целиком выполнена
другим потоком, либо не выполнена даже частично.

---

#### Составные действия (compound actions)

Последовательность операций, которые должны выполняться
атомарно, чтобы оставаться потокобезопасными.

---

#### Мониторный/внутренний замок (monitor/intrinsic lock)

Java предоставляет встроенный замковый механизм для усиления
атомарности — синхронизированный блок, состоящий из ссылки на
объект-замок (lock) и блока кода, который будет им защищен. 
Ключевое слово `synchronized` является условным обозначением и метода,
и замка. Статические синхронизированные методы используют объект
`Class`.

```java
synchronized (lock) {
    // Обратиться к защищаемому замком совместному состоянию либо его изменить
}
```

Каждый объект Java может неявно действовать как lock для целей
синхронизации, то есть являться внутренним замком (intrinsic lock)
или мониторным замком (monitor lock). Lock автоматически приобретается выполняющим потоком 
перед входом в синхронизированный блок и автоматически освобождается, когда управление выходит из
синхронизированного блока: либо обычным путем выполнения кода,
либо путем исключения из блока. Приобрести внутренний lock можно
только при входе в синхронизированный блок или в метод, защищенный
этим lock.

---

#### Мьютекс (mutex)

Внутренние замки в Java действуют как взаимоисключающие locks —
мьютексы (mutual exclusion locks). Это означает, что замком может владеть не более чем один поток. Когда поток А пытается приобрести lock,
которым владеет поток В, он должен ждать или блокировать продвижение
до тех пор, пока В его не освободит. Если В не освободит lock никогда,
то А будет ждать вечно. Поскольку только один поток за раз может выполнять блок кода, 
защищенный замком, синхронизированные блоки, защищенные тем же замком,
выполняются атомарно. Никакой поток, выполняющий синхронизированный блок, не может наблюдать другой поток в синхронизированном
блоке, защищенном тем же замком.

---

#### Повторно входимые внутренние замки (reentrant intrinsic lock)

Когда поток запрашивает `lock`, которым уже владеет другой поток, он
блокирует продвижение. Но так как внутренние `locks` являются повторно входимыми (reentrant),
если поток пытается приобрести `lock`,
которым он уже владеет, то запрос выполнится успешно. Повторная
входимость означает, что `locks` приобретаются в расчете на один поток,
а не в расчете на один вызов, путем ассоциирования потоков с `locks`.

---

#### Volatile-переменные

Язык Java также предоставляет альтернативную, более слабую форму
синхронизации — использование volatile-переменных, обновления
которых распространяются предсказуемо всеми потоками. Переменная
`volatile` для компилятора и рабочей среды является совместной, то есть
операции над ней не будут переупорядочены с другими операциями в памяти. Volatile-переменные
не кэшируются в регистрах или кэшах, где данные скрыты от других процессоров, 
поэтому их чтение всегда возвращает самый последний результат операций записи.
Обращение к volatile-переменной не может побудить выполняющий поток к блокированию, 
что делает ее легковесным механизмом синхронизации.

Использование volatile-переменных оправданно при следующих
условиях:

1) Записи в переменную не зависят от ее текущего значения, либо есть
гарантия, что значения переменной обновляются только одним потоком.
2) Переменная не участвует в инвариантах с другими переменными состояния.
3) При обращении к переменной заранее не требуется блокировка.

---

#### Безопасность из ниоткуда

Когда поток читает переменную без синхронизации, он может увидеть
устаревшее значение, но можно утверждать, что это значение было помещено туда каким-то потоком,
а не возникло случайно. Эта гарантия безопасности называется _безопасностью из ниоткуда_.

Она применима ко всем переменным с одним исключением: 64-разрядные числовые переменные 
(с типом double и long), которые не объявлены `volatile`. Модель памяти Java требует,
чтобы операции доставки из памяти и сохранения в память были атомарными, но переменным типов
`double` и `long` разрешено воспринимать 64-разрядное чтение и запись как две отдельные 32-разрядные
операции. Если чтения и записи происходят в разных потоках, то при чтении переменной `long`
можно получить назад верхние 32 бита одного значения и нижние 32 бита другого. Таким образом,
использовать в многопоточных программах совместные `mutable` переменные с типом
`double` и `long` небезопасно, если они не объявлены `volatile` или не защищены замком.

---

####  Публикация (publishing) объекта
Это означает его доступность за пределами текущей области действия. Например, ссылка на объект
может позволить другому коду вернуть его из неприватного метода или передать его методу
в другом классе. Публикация переменных внутреннего состояния препятствует инкапсуляции и
соблюдению инвариантов, а публикация объектов до момента их полного конструирования ставит
под угрозу потокобезопасность.

Самая простая форма публикации — это ссылка в публичном статическом поле. 
Метод `initialize` создает и публикует экземпляр нового хеш-множества, сохраняя ссылку
на него в `secrets`.

```java
public static Set<Secret> secrets;

public void initializeO {
    secrets = new HashSet<>();
}
```

Публикация одного объекта может косвенно публиковать другие. Если вы добавите Secret
в опубликованное множество `secrets`, то дополнение также опубликуется, 
потому что любой код может выполнить итеративный обход множества и получить ссылку
на новый Secret.

Еще одним механизмом публикации объекта или его внутреннего состояния является публикация
экземпляра внутреннего класса. Когда класс `ThisEscape` публикует слушателя `EventListener`,
он неявно публикует и окаймляющий его экземпляр `ThisEscape`, потому что экземпляры
внутреннего класса содержат скрытую ссылку на него.

```java
@NotThreadSafe
public class ThisEscape {
    
    public ThisEscape(EventSource source) {
        source.registerListener(
            new EventListener() {
                public void onEvent(Event e) {
                    doSomething(e);
                }
            });
    }
}
```

```java
@ThreadSafe
public class SafeListener {

    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    public static SafeListener newInstance(Eventsource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }
}
```

---

#### Ускользнувший (escaped) объект

Объект, который не вовремя публикуется.

---

#### Immutable объект

Объект является immutable, если:

1) Его состояние невозможно изменить после конструирования.
2) Все поля являются final.
3) Он надлежаще сконструирован (ссылка this не ускользает).

---

#### Фактически immutable объекты (effectively immutable)

Объекты, которые не являются immutable, но состояние которых не
будет изменено после публикации, называются фактически immutable.

---

</details>



<details>
<summary>
Рекомендации
</summary>

---

Для сохранения непротиворечивости состояний обновляйте состояния родственных
переменных в единой атомарной операции.

```java
@NotThreadSafe
public class NumberTracker {
    
    private int previousNumber;
    private int currentNumber;
    
    public void updateNumber(int number) {
        previousNumber = currentNumber;
        currentNumber = number;
    }
}
```

```java
@ThreadSafe
public class NumberTracker {
    
    private int previousNumber;
    private int currentNumber;
    
    public synchronized void updateNumber(int number) {
        previousNumber = currentNumber;
        currentNumber = number;
    }
}
```

---

Избегайте удержания блокировки во время длительных вычислений или
операций, таких как сетевой или консольный ввод-вывод.

[Пример](concurrency-examples-5-servlet-attempts-to-cache-its-last-result)

---

Чтобы обеспечить видимость актуальных значений совместных переменных, 
синхронизируйте читающие и пишущие потоки на общем замке.

---

Не позволяйте ссылке `this` ускользнуть во время конструирования.

Распространенной ошибкой, позволяющей ссылке `this` ускользнуть,
является запуск потока из конструктора. Когда объект создает поток из
своего конструктора, он почти всегда делится своей ссылкой `this` с новым
потоком, явно или неявно. Тогда новый поток видит владеющий объект
до своего окончательного конструирования. Нет ничего плохого в создании потока в конструкторе, но лучше
не запускать поток сразу. Вместо этого добавьте метод start или initialize, запускающий собственный поток.
Это позволит делиться объектом, построение которого гарантированно завершено.

```java
@NotThreadSafe
public class ThreadExecutorService {
    
    private ExecutorService executor;

    public ThreadExecutorService() {
        this.executor = Executors.newSingleThreadExecutor();
        executor.execute(this::doSomething);
    }
    
    private void doSomething() {
        // code
    }
}
```

```java
@ThreadSafe
public class ThreadExecutorService {
    
    private ExecutorService executor;

    public ThreadExecutorService() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void start() {
        executor.execute(this::doSomething);
    }

    private void doSomething() {
        // code
    }
}
```

---

Способы обеспечения потокобезопасности классов без необходимости синхронизации включают:

1) [Ограничением стеком](concurrency-examples-9.1-stack-confinement).
2) [ThreadLocal](concurrency-examples-9.2-thread-local).
3) [Immutable объекты](concurrency-examples-9.3-immutable-class).

---

</details>



<details>
<summary>
Утверждения
</summary>

---

Потокобезопасные классы инкапсулируют любую необходимую синхронизацию сами и не нуждаются в помощи клиента.
Ни один набор операций, выполняемых последовательно либо конкурентно на экземплярах потокобезопасного класса,
не может побудить экземпляр находиться в недопустимом состоянии.

---

Все обращения к mutable переменной должны выполняться с удержанием одного и того же `lock`. 
Только тогда переменная будет надежно защищена этим `lock` от одновременного доступа нескольких потоков.

Распространенная ошибка — считать, что синхронизация должна использоваться только во время записи
в совместные переменные.

[Пример](concurrency-examples-8-mutable-integer)

---

Каждая совместная mutable переменная должна быть защищена
только одним замком.

---

Когда каждый доступ к переменной осуществляется с удержанием `lock`, только один поток за раз может к ней
обратиться. Когда класс имеет инварианты, включающие более одной переменной состояния, каждая переменная,
участвующая в инварианте, должна быть защищена тем же `lock`. Это позволит обращаться к переменным или
обновлять их в единой атомарной операции, соблюдая инвариант.

[Пример](concurrency-examples-5-servlet-attempts-to-cache-its-last-result)

---

Когда поток А выполняет синхронизированный блок, а затем поток B входит в синхронизированный блок,
защищенный тем же `lock`, значения переменных, которые были видны потоку А до освобождения `lock`, 
будут видны потоку B по приобретении `lock`. Без синхронизации видимость не гарантирована.

[Пример](concurrency-examples-8-mutable-integer)

---

Без синхронизации компилятор, процессор и рабочая среда могут запутать порядок выполнения операций.
Не стоит ожидать естественного порядка действий памяти в недостаточно синхронизированных
многопоточных программах.

[Пример](concurrency-examples-7-sharing-variables-without-sync)

---

Эффекты видимости volatile-переменной выходят за пределы ее значения. Когда поток А пишет значение
в volatile-переменную и затем поток В ее читает, значения всех переменных, которые были видны до этой
записи, становятся видимыми потоку В. Запись в volatile-переменную
похожа на выход из синхронизированного блока, а ее чтение — на вход
в него.

В любом случае на экран будет выводиться текст "Значение переменной number: 5". 
Однако, если убрать ключевое слово volatile или поменять порядок операций присваивания, 
то возможен вывод "Значение переменной number: 0", что является следствием нарушения потокобезопасности.

```java
@ThreadSafe
public class VolatileVisibility {

    private volatile boolean flag;
    private int number;

    public void writer() {
        number = 5;
        flag = true;
    }

    public void reader() {
        while (!flag) {
            Thread.yield();
        }

        System.out.println("Значение переменной number: " + number);
    }

    public static void main(String[] args) {
        VolatileVisibility object = new VolatileVisibility();

        Thread a = new Thread(object::writer);
        Thread b = new Thread(object::reader);

        a.start();
        b.start();
    }
}
```

---

Блокировка может гарантировать как видимость, так и атомарность,
а volatile-переменные гарантируют только видимость.

---

Immutable объекты всегда являются потокобезопасными.

[Пример1](concurrency-examples-5-servlet-attempts-to-cache-its-last-result)

[Пример2](concurrency-examples-9.3-immutable-class)

---

Immutable объекты могут безопасно использоваться потоками без
дополнительной синхронизации, даже когда синхронизация для их публикации не используется.
Однако если final поля ссылаются на mutable объекты, то
синхронизация по-прежнему необходима для доступа к состоянию этих
объектов.

[Пример](concurrency-examples-10-safe-publication)

---

Безопасную публикацию mutable объекта, при которой ссылка на него и его
состояние видна всем потокам в одно и то же время, можно провести
с помощью:

• инициализации объектной ссылки из статического инициализатора;

• сохранения ссылки на него в volatile-поле либо в AtomicReference;

• сохранения ссылки на него в final поле надлежаще сконструированного объекта;

• сохранения ссылки на него в поле, которое надлежаще защищается
lock.

[Пример](concurrency-examples-10-safe-publication)

---

Если поток `А` помещает объект X в потокобезопасную коллекцию `Vector`
или `synchronizedList`, а затем поток `В` извлекает его, то `В` гарантированно
видит состояние X в том виде, в каком `А` его оставил, даже если передающий код не имеет явной синхронизации.
Потокобезопасные библиотечные коллекции предлагают следующие гарантии безопасной публикации:

• ключ или значение, размещенные в Hashtable, synchronizedMap либо ConcurrentMap, безопасно публикуются в
любом потоке, который извлекает их из ассоциативного массива Мар (напрямую или через итератор);

• элемент, размещенный в `Vector`, `CopyOnWriteArrayList`, `CopyOnWriteArraySet`, `synchronizedList` 
либо `synchronizedSet`, безопасно публикуется в любом потоке, который извлекает его из коллекции;

• элемент, размещенный в `BlockingQueue` либо `ConcurrentLinkedQueue`, безопасно публикуется в любом потоке, который
извлекает его из очереди.

---

Безопасно опубликованные фактически immutable объекты могут
безопасно использоваться любым потоком без дополнительной синхронизации.

Например, класс Date является mutable, но если вы используете его
как immutable, то сэкономите на блокировке. Предположим, вы хотите поддержать ассоциативный массив Мар, 
хранящий время последнего входа каждого пользователя в систему:

```java
public Map<String, Date> lastLogin = Collections.synchronizedMap(new HashMap<>());
```

Если значения Date не изменятся после их размещения в массиве Мар, то
синхронизации в реализации synchronizedMap достаточно для безопасной
публикации значений Date, и при доступе к ним дополнительная синхронизация не потребуется.

---

Требования к публикации объекта зависят от его изменяемости:

• immutable объекты могут быть опубликованы любым механизмом;

• фактически immutable объекты должны быть безопасно опубликованы;

• mutable объекты должны быть безопасно опубликованы и быть либо потокобезопасными, либо защищенными lock.

---

</details>