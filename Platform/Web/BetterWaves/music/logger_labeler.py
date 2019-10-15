def logger(logger):
    def wrapper(*args, **kwargs):
        print(logger + ": ", end="")
        print(*args, **kwargs)
    return wrapper