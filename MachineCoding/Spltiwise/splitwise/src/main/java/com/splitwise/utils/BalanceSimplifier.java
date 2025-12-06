package com.splitwise.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class BalanceSimplifier {
    @Getter
    @Setter
    @AllArgsConstructor
    private static class MemberBalance implements Comparable<MemberBalance>{
        private String userId;
        private Double amount;
        
        public void reduceAmount(double amt) {
            amount-=amt;
        }

        @Override
        public int compareTo(MemberBalance that) {
            return Double.compare(that.amount, this.getAmount());
        }
    }

    static Map<String, Map<String, Double>> simplified = new HashMap<>();
    static PriorityQueue<MemberBalance> senders = new PriorityQueue<>();
    static PriorityQueue<MemberBalance> receivers = new PriorityQueue<>();

    public static Map<String, Map<String, Double>> simplify(Map<String, Map<String, Double>> userBalances) {
        simplified = new HashMap<>();
        senders = new PriorityQueue<>();
        receivers = new PriorityQueue<>();

        for (Map.Entry<String, Map<String, Double>> entry : userBalances.entrySet()) {
            String userId = entry.getKey();
            simplified.put(userId, new HashMap<>());
            double moneyFlow = 0.0;
            for (Map.Entry<String, Double> userEntry : entry.getValue().entrySet()) {
                moneyFlow+=userEntry.getValue();
            }

            if (moneyFlow > 0) receivers.add(new MemberBalance(userId, moneyFlow));
            else senders.add(new MemberBalance(userId, Math.abs(moneyFlow)));
        }

        while (!senders.isEmpty() && !receivers.isEmpty()) {
            MemberBalance sender = senders.poll();
            MemberBalance receiver = receivers.poll();

            double amount = Math.min(sender.getAmount(), receiver.getAmount());
            sender.reduceAmount(amount);
            receiver.reduceAmount(amount);

            if (sender.getAmount() > 0) senders.add(sender);
            if (receiver.getAmount() > 0) receivers.add(receiver);

            simplified.putIfAbsent(sender.getUserId(), new HashMap<>());
            simplified.putIfAbsent(receiver.getUserId(), new HashMap<>());

            simplified.get(sender.getUserId()).putIfAbsent(receiver.getUserId(), 0.0);
            Double current = simplified.get(sender.getUserId()).get(receiver.getUserId());
            simplified.get(sender.getUserId()).put(receiver.getUserId(), current - amount);

            simplified.get(receiver.getUserId()).putIfAbsent(sender.getUserId(), 0.0);
            current = simplified.get(receiver.getUserId()).get(sender.getUserId());
            simplified.get(receiver.getUserId()).put(sender.getUserId(), current + amount);
        }

        return simplified;
    }
}
