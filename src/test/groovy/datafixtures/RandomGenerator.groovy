package datafixtures

class RandomGenerator {
    static def randomString() {
        return UUID.randomUUID().toString()
    }

    static def randomLong() {
        return new Random().nextLong()
     }

    static def randomBigDecimal() {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        int scale = random.nextInt(10); // You can adjust the scale as needed
        return new BigDecimal(randomDouble).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
}
