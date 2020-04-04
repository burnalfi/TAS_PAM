from backend.model.credentials import Credentials

class CredentialController:

    def __init__(self, db):
        self.db = db


    def get_by_id(self, id : str):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM credentials where id='{}'".format(id))
        result = cur.fetchone()
        cred = Credentials(result[0], result[1], result[2], result[3])
        return cred

    def get_all(self):
        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("SELECT * FROM credentials")
        return [Credentials(i[0], i[1], i[2], i[3]) for i in cur.fetchall()]

    def insert_credential(self, credential):
        identif = credential.id
        name = credential.name
        number = credential.number
        secondnumber = credential.secondnumber

        conn = self.db.get_connection() 
        cur = conn.cursor()
        cur.execute("INSERT INTO credentials(id, name, number, secondnumber) VALUES (%s, %s, %s, %s)", (identif, name, number, secondnumber))
        
        conn.commit()

