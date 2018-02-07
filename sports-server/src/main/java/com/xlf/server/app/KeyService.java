package com.xlf.server.app;


import java.util.Set;

public interface KeyService {

    public String getTimeBettingKeyPrefix(String userId, Integer serialNumber);

    public String getTimeBettingLocationKey(String userId, Integer serialNumber, Integer index);


    public Long saddTimeSetMember(String userId, Integer serialNumber, Integer index, Integer digital);


    public Set<String> getTimeSetMembers(String userId, Integer serialNumber, Integer index);

    public Long getTimeSetSize(String userId, Integer serialNumber, Integer index);

    public Long timebettingHset(String userId, Integer serialNumber, Integer digital, Long count);

    public Long timebettingHget(String userId, Integer serialNumber, Integer digital);

    public String getRacingBettingKeyPrefix(String userId, Integer serialNumber);

    public String getRacingBettingLocationKey(String userId, Integer serialNumber, Integer index);


    public Set<String> getRacingSetMembers(String userId, Integer serialNumber, Integer index);

    public Long saddRacingSetMember(String userId, Integer serialNumber, Integer index, Integer digital);

    public Long racingBettingHset(String userId, Integer serialNumber, Integer digital, Long count);

    public Long racingBettingHget(String userId, Integer serialNumber, Integer digital);
}
