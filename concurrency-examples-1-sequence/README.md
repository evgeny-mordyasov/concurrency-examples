Операция приращения `value++` не является _атомарной_ (atomic), то есть неделимой, а состоит из трех отдельных операций: чтения значения,
добавления в него единицы и записи нового значения. Поскольку операции в потоках произвольно перемежаются, два потока могут прочитать
`value` в одно и то же время, добавить в него единицу и вернуть одно
и то же значение.