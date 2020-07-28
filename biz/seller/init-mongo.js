db.createUser(
  {
    user: "msaBizSeller",
    pwd: "MsaBizSeller1!",
    roles: [
      {
        role: "readWrite",
        db: "msa_simple_mall"
      }
    ]
  }
)
