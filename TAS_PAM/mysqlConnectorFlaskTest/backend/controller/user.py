from backend.model.user import User

class UserController:

    def __init__(self, db):
        self.db = db


    def get_by_user(self, user : str):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM user where username='{}'".format(user))
        result = cur.fetchone()
        user = User(result[0], result[1])
        return user

    def get_all(self):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM user")
        return [User(i[0], i[1]) for i in cur.fetchall()]

    def insert_user(self, user):
        username = user.username
        password = user.password

        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("INSERT INTO user(username, password) VALUES (%s, %s)", (username, password))
        
        conn.commit()