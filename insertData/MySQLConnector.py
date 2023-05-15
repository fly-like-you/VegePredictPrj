import pymysql


class MySQLConnector:
    def __init__(self, host="localhost", user="root", password="q!w2e3r4t5", database="VegetablePrediction", port=3306):
        self.conn = None
        self.host = host
        self.user = user
        self.password = password
        self.database = database
        self.port = port
        self.connect()

    def connect(self):
        self.conn = pymysql.connect(
            host=self.host,
            user=self.user,
            password=self.password,
            database=self.database,
            port=self.port,
        )

    def execute(self, query, args=None):
        cursor = self.conn.cursor()
        cursor.execute(query, args)
        self.conn.commit()
        cursor.close()

    def executemany(self, query, args=None):
        cursor = self.conn.cursor()
        cursor.executemany(query, args)
        self.conn.commit()
        cursor.close()

    def close(self):
        self.conn.close()


