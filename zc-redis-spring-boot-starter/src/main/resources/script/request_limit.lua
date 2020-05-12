local key = KEYS[1]
local value = 1
local limit = tonumber(ARGV[1])
local expire = ARGV[2]

if redis.call("SET", key, value, "NX", "PX", expire) then
    return 1
else
    if redis.call("INCR", key) <= limit then
        return 1
    end
    if redis.call("TTL", key) == -1 then
        redis.call("PEXPIRE", key, expire)
    end
end
return 0