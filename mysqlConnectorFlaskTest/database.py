import mysql.connector
from mysql.connector import errorcode

# try:
#     conn = mysql.connector.connect(user='root', password='',
#                                     host="127.0.0.1", database='flasktest')
# except mysql.connector.Error as err:
#     if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
#         print("Something is wrong with your user name or password")
#     elif err.errno == errorcode.ER_BAD_DB_ERROR:
#         print("Database does not exist")
#     else:
#         print(err)
# else:
#     print("connection success!")
#     conn.close()

class Database:

    def __init__(self, user="", password="", host="", database=""):
        self.user = user
        self.password = password
        self.host = host
        self.database = database

        self.connection = None

    def connect(self):
        self.connection = mysql.connector.connect(username = self.user, password = self.password, host = self.host, database = self.database)

        if self.connection.is_connected:
            print("Connection Succes!")
            return self.connection.is_connected

        else:
            print("Connection Failed")

    def get_connection(self):
        if self.connect():
            return self.connection

