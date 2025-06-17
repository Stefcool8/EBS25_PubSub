# Raport de evaluare – Sistem Publish/Subscribe Content-Based

## Descriere proiect
Am implementat o arhitectură publish/subscribe content-based folosind:
- **Publisher**: generează un flux de publicații meteorologice (Protocol Buffers + Storm + Kafka)
- **Brokeri**: 3 noduri interconectate care filtrează publicațiile după conținut și notifică subscrieri simple sau complexe (tumbling window)
- **Subscriberi**: 3 procese separate, fiecare cu ~3 333 subscrieri simple generate aleator, care primesc notificări prin callback REST

Evaluarea sistemului s-a făcut pe un feed continuu de 3 minute, cu 10 000 de subscripții simple și măsurători de livrări, latență și rată de potrivire.

---

## a) Număr publicații livrate cu succes în 3 minute
Fiecare subscriber avea ~3 333 subscrieri. Pentru fiecare subscriber s-a calculat:
- **Expected publications delivered** = numărul de publicații care se potriveau cu cel puțin o subscriere
- **Actual publications delivered** = numărul de confirmații primite de la subscriber

```text
Subscriber 1:
  Expected publications delivered: 1710
  Actual   publications delivered: 1710
  Percentage of publications delivered: 100.00%
-----------------------------
Subscriber 2:
  Expected publications delivered: 1710
  Actual   publications delivered: 1710
  Percentage of publications delivered: 100.00%
-----------------------------
Subscriber 3:
  Expected publications delivered: 1710
  Actual   publications delivered: 1710
  Percentage of publications delivered: 100.00%
-----------------------------
Total:
  Expected publications delivered: 5130
  Actual   publications delivered: 5130
  Percentage of publications delivered: 100.00%
```

## b) Latența medie de livrare
S-au colectat perechi (emittedAt, receivedAt) și s-au calculat:

```text
Evaluated 5130 deliveries across 3 subscribers.
Average delivery latency: 65351.58 ms
Min latency: 3813 ms, Max latency: 118841 ms
```

- Număr total livrări evaluate: 5 130 
- Latența medie: 65 351.58 ms 
- Latența minimă: 3 813 ms 
- Latența maximă: 118 841 ms

## c) Rata de potrivire a subscrierilor (matching rate)
S-au generat două seturi de 10 000 subscrieri simple pe câmpul city:

1. 100% egalitate (city == X pentru toate subscrierile)
2. 25% egalitate (city == X pentru 25% din subscrieri, city != X pentru 75%)

```text
Matching rate with 100% equals on field: 6.00%
Matching rate with  25% equals on field: 8.26%
```

- Cu 100% subscrieri city == X, doar ~6% din publicații se potrivesc. 
- Cu 25% subscrieri city == X, restul de 75% nu filtrează după city și permit mai multe potriviri, crescând rata la ~8.3%.
