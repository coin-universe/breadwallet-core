package com.breadwallet.crypto;

import org.junit.Test;
import static org.junit.Assert.*;

public class AmountAIT {

    @Test
    public void testAmountCreateBtc() {
        Currency btc = new Currency("Bitcoin", "Bitcoin", "BTC", "native");
        Unit satoshi_btc = new Unit(btc, "BTC-SAT", "Satoshi", "SAT");
        Unit btc_btc = new Unit(btc, "BTC-BTC", "Bitcoin", "B", satoshi_btc, (byte) 8);

        Amount btc1 = Amount.create(100000000, satoshi_btc);
        assertEquals(new Double(100000000), btc1.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(1), btc1.doubleAmount(btc_btc).get());
        assertFalse(btc1.isNegative());

        Amount btc1n = btc1.negate();
        assertEquals(new Double(-100000000), btc1n.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(-1), btc1n.doubleAmount(btc_btc).get());
        assertTrue(btc1n.isNegative());
        assertFalse(btc1n.negate().isNegative());

        Amount btc2 = Amount.create(1, btc_btc);
        assertEquals(new Double(1), btc1.doubleAmount(btc_btc).get());
        assertEquals(new Double(100000000), btc1.doubleAmount(satoshi_btc).get());

        assertEquals(btc1, btc2);

        Amount btc3 = Amount.create(1.5, btc_btc);
        assertEquals(new Double(1.5), btc3.doubleAmount(btc_btc).get());
        assertEquals(new Double(150000000), btc3.doubleAmount(satoshi_btc).get());

        Amount btc4 = Amount.create(-1.5, btc_btc);
        assertTrue(btc4.isNegative());
        assertEquals(new Double(-1.5), btc4.doubleAmount(btc_btc).get());
        assertEquals(new Double(-150000000), btc4.doubleAmount(satoshi_btc).get());

        // TODO: Add in formatting tests
    }

    @Test
    public void testAmountCreateBtcString() {
        Currency btc = new Currency("Bitcoin", "Bitcoin", "BTC", "native");
        Unit satoshi_btc = new Unit(btc, "BTC-SAT", "Satoshi", "SAT");
        Unit btc_btc = new Unit(btc, "BTC-BTC", "Bitcoin", "B", satoshi_btc, (byte) 8);

        Amount btc1s = Amount.create("100000000", false, satoshi_btc).get();
        assertEquals(new Double(100000000), btc1s.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(1), btc1s.doubleAmount(btc_btc).get());

        Amount btc2s = Amount.create("1", false, btc_btc).get();
        assertEquals(new Double(100000000), btc2s.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(1), btc2s.doubleAmount(btc_btc).get());

        assertEquals(btc1s, btc2s);

        Amount btc3s = Amount.create("0x5f5e100", false, satoshi_btc).get();
        assertEquals(new Double(100000000), btc3s.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(1), btc3s.doubleAmount(btc_btc).get());
        // TODO: Add string tests

        Amount btc4s = Amount.create("0x5f5e100", true, satoshi_btc).get();
        assertEquals(new Double(-100000000), btc4s.doubleAmount(satoshi_btc).get());
        assertEquals(new Double(-1), btc4s.doubleAmount(btc_btc).get());
        // TODO: Add string tests

        // TODO: Believe this is a bug in core; fix and re-enable
//        assertFalse(Amount.create("w0x5f5e100", false, satoshi_btc).isPresent());
        assertFalse(Amount.create("0x5f5e100w", false, satoshi_btc).isPresent());
        assertFalse(Amount.create("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000", false, satoshi_btc).isPresent());

        assertFalse(Amount.create("-1", false, satoshi_btc).isPresent());
        assertFalse(Amount.create("+1", false, satoshi_btc).isPresent());
    }

    @Test
    public void testAmountCreateEth() {
        Currency eth = new Currency("Ethereum", "Ethereum", "ETH", "native");

        Unit wei_eth = new Unit(eth, "ETH-WEI", "WEI", "wei");
        Unit gwei_eth = new Unit(eth, "ETH-GWEI", "GWEI",  "gwei", wei_eth, (byte) 9);
        Unit ether_eth = new Unit(eth, "ETH-ETH", "ETHER", "E",    wei_eth, (byte) 18);

        Amount eth1 = Amount.create(1e9, gwei_eth);
        Amount eth2 = Amount.create(1.0, ether_eth);
        Amount eth3 = Amount.create(1.1, ether_eth);

        assertEquals(eth1, eth2);
        assertTrue(eth1.compareTo(eth3) < 0);
        assertNotEquals(eth1.compareTo(eth3), 0);
        assertEquals(eth1.add(eth1), eth2.add(eth2));

        assertEquals(new Double(0.0), eth2.sub(eth1).get().doubleAmount(wei_eth).get());
        assertEquals(new Double(0.0), eth2.sub(eth1).get().doubleAmount(ether_eth).get());
        assertEquals(new Double(2.0), eth2.add(eth1).get().doubleAmount(ether_eth).get());

        assertTrue(eth2.sub(eth3).get().isNegative());
        assertFalse(eth2.sub(eth2).get().isNegative());
    }
}
