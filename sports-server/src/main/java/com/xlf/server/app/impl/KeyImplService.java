
package com.xlf.server.app.impl;


import com.xlf.common.enums.RedisKeyEnum;
import com.xlf.common.service.RedisService;
import com.xlf.server.app.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service
public class KeyImplService implements KeyService {
    @Autowired
    private RedisService redisService;

    public String getTimeBettingKeyPrefix(String userId, Integer serialNumber) {
        return RedisKeyEnum.TIME_BETTIING_ISSUNO.getKey () + ":" + userId + ":" + serialNumber;
    }

    public String getTimeBettingLocationKey(String userId, Integer serialNumber, Integer index) {
        return RedisKeyEnum.TIME_BETTIING_ISSUNO.getKey () + ":" + userId + ":" + serialNumber + ":" + index;
    }


    public Set<String> getTimeSetMembers(String userId, Integer serialNumber, Integer index) {
        return redisService.smembers (getTimeBettingLocationKey (userId, serialNumber, index));
    }


    public Long getTimeSetSize(String userId, Integer serialNumber, Integer index) {
        return redisService.scard (getTimeBettingLocationKey (userId, serialNumber, index));
    }

    public Long timebettingHset(String userId, Integer serialNumber, Integer digital, Long count) {
        if (count == null) {
            count = 0L;
        }

        return redisService.hset (getTimeBettingKeyPrefix (userId, serialNumber), digital + "", count + "");
    }

    public Long timebettingHget(String userId, Integer serialNumber, Integer digital) {
        String countStr = redisService.hget (getTimeBettingKeyPrefix (userId, serialNumber), digital + "");
        if (StringUtils.isEmpty (countStr)) {
            countStr = "0";
        }
        return Long.valueOf (countStr);
    }

    @Override
    public Long saddTimeSetMember(String userId, Integer serialNumber, Integer index, Integer digital) {
        return redisService.sadd (getTimeBettingLocationKey (userId, serialNumber, index), digital + "");
    }

    @Override
    public String getRacingBettingLocationKey(String userId, Integer serialNumber, Integer index) {
        return RedisKeyEnum.RACING_BETTIING_ISSUNO.getKey () + ":" + userId + ":" + serialNumber + ":" + index;
    }

    public String getRacingBettingKeyPrefix(String userId, Integer serialNumber) {
        return RedisKeyEnum.RACING_BETTIING_ISSUNO.getKey () + ":" + userId + ":" + serialNumber;
    }

    @Override
    public Set<String> getRacingSetMembers(String userId, Integer serialNumber, Integer index) {
        return redisService.smembers (getRacingBettingLocationKey (userId, serialNumber, index));
    }


    @Override
    public Long saddRacingSetMember(String userId, Integer serialNumber, Integer index, Integer digital) {
        return redisService.sadd (getRacingBettingLocationKey (userId, serialNumber, index), digital + "");
    }

    @Override
    public Long racingBettingHset(String userId, Integer serialNumber, Integer digital, Long count) {
        if (count == null) {
            count = 0L;
        }

        return redisService.hset (getRacingBettingKeyPrefix (userId, serialNumber), digital + "", count + "");
    }

    @Override
    public Long racingBettingHget(String userId, Integer serialNumber, Integer digital) {
        String countStr = redisService.hget (getRacingBettingKeyPrefix (userId, serialNumber), digital + "");
        if (StringUtils.isEmpty (countStr)) {
            countStr = "0";
        }
        return Long.valueOf (countStr);
    }
}

