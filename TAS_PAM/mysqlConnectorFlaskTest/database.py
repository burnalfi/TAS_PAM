import mysql.connector
from mysql.connector import errorcode


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

